import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:nexxplay/nexxplay.dart';

void main() => runApp(const NexxExampleApp());

class NexxExampleApp extends StatelessWidget {
  const NexxExampleApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'nexxPLAY Flutter Testing',
      home: NavigationPage(),
    );
  }
}

class NavigationPage extends StatelessWidget {
  const NavigationPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Navigation page')),
      body: Center(
        child: ElevatedButton(
          onPressed: () {
            Navigator.of(context).push<void>(
              MaterialPageRoute(builder: (_) => const _NexxPlayPage()),
            );
          },
          child: const Text('Launch player'),
        ),
      ),
    );
  }
}

// `INTEGRATION_GUIDE` Flutter configuration.
/// This page manages the player instance and shows how to enable Fullscreen and
/// configure PiP mode properly.
///
/// 1. Initialization
/// First of all, we need to instantiate the player, which is done
/// automatically on the first player inflation into the RenderObject hierarchy.
/// The corresponding NexxPlayController will be returned from the
/// initialization callback.
///
/// 2. Starting & Events Observation
/// NexxPlayController is used to start the player. Also serves as a
/// gateway to player events observation. More details are available in the
/// `_buildPlayer`, `_startPlayer`, `onPlayerEvent` and `_consumeEvent`
/// methods' definitions.
///
/// 3. Fullscreen Support
/// Fullscreen support is done by simply moving the player widget to a
/// different tree part where it is expanded to the full height & width. It
/// can be achieved by using a GlobalKey instance. It is implemented around the
/// next set of properties, methods and classes: `_playerKey`, `_mode`,
/// `_modeTransformation`, `_consumeEvent`, `PlayerEventVisitor` (and
/// `AdHocVisitor`), `onPlayerEvent` and `_buildFullscreenPlayerPage()`.
/// "Entrypoint" method is `onPlayerEvent`.
///
/// 4. PiP Support
/// Picture in picture mode is handled automatically by the player, and it is
/// only needed to expand the player to full screen when in PiP, which is
/// implemented as a subset of fullscreen implemenetation in a way that
/// both `isFullscreen` and `isInPIPMode` trigger rendering the player in
/// the fullscreen mode. "Entrypoint" method is `onPlayerEvent`.
///
class _NexxPlayPage extends StatefulWidget {
  const _NexxPlayPage({Key? key}) : super(key: key);

  @override
  _NexxPlayPageState createState() => _NexxPlayPageState();
}

class _NexxPlayPageState extends State<_NexxPlayPage> with AdHocVisitor<void> {
  @override
  Widget build(BuildContext context) => _buildPage();

  Widget _buildPage() {
    return _mode.shouldExpand
        ? _buildFullscreenPlayerPage()
        : _buildNonFullScreenPlayerPage();
  }

  Widget _buildFullscreenPlayerPage() {
    return ScaffoldMessenger(
      key: _messengerKey,
      child: Scaffold(
        body: _buildPlayer(),
      ),
    );
  }

  Widget _buildNonFullScreenPlayerPage() {
    return ScaffoldMessenger(
      key: _messengerKey,
      child: Scaffold(
        appBar: AppBar(
          title: const Text('nexxPLAY example app'),
          actions: _controller == null
              ? []
              : [
                  PopupMenuButton<String>(
                    onSelected: _handleOptionSelection,
                    itemBuilder: (_) => _optionMap.keys
                        .map((o) =>
                            PopupMenuItem<String>(value: o, child: Text(o)))
                        .toList(),
                  ),
                ],
        ),
        body: Center(child: _buildContent()),
      ),
    );
  }

  void _handleOptionSelection(String option) => _optionMap[option]?.call(this);

  Widget _buildContent() {
    return Column(
      children: [
        Expanded(child: _buildPlayer()),
        Expanded(child: _buildEventsList()),
      ],
    );
  }

  Widget _buildPlayer() {
    return NexxPlay(
      key: _playerKey,
      environment: _environment,
      configuration: _configuration,
      onControllerCreated: _startPlayer,
    );
  }

  Widget _buildEventsList() {
    return ListView(
      children: _events.reversed
          .map((e) => e.visit(const _WidgetPlayerEventVisitor()))
          .toList(),
    );
  }

  Future<void> _startPlayer(NexxPlayController controller) async {
    try {
      await controller.startPlay(
        playMode: 'video',
        mediaID: '#TODO',
        configuration: _configuration,
      );
      if (!mounted) return;
      _subscribe(controller);
      _controller = controller;
    } on Object catch (e, st) {
      _report('Nexx: exception occurred during player start: \n$e\n$st');
    }
  }

  void _report(String message) {
    debugPrint(message);
    _messengerKey.currentState?.showSnackBar(SnackBar(content: Text(message)));
  }

  void _subscribe(NexxPlayController controller) {
    _subscription = controller.events().listen(
      _consumeEvent,
      onError: (Object e, StackTrace st) {
        _report('Error occurred during events listening: $e');
        debugPrintStack(
          stackTrace: st,
          label: 'Player events error stacktrace',
        );
      },
    );
  }

  void _consumeEvent(PlayerEvent event) {
    event.visit(this);
    _events.add(event);
    if (!_mode.shouldExpand) setState(() {});
  }

  @override
  void onPlayerEvent(DirectPlayerEvent event) {
    final newMode = _modeTransformation[event.type]?.call(_mode);
    if (newMode != null) setState(() => _mode = newMode);
  }

  @override
  void dispose() {
    _dispose();
    super.dispose();
  }

  Future<void> _dispose() async {
    await _subscription?.cancel();
    _subscription = null;
    _controller?.dispose();
    _controller = null;
  }

  NexxPlayController? _controller;
  StreamSubscription<PlayerEvent>? _subscription;
  _PlayerMode _mode = const _PlayerMode.initial();
  final List<PlayerEvent> _events = [];
  final _playerKey = GlobalKey<NexxPlayState>();
  final _messengerKey = GlobalKey<ScaffoldMessengerState>();

  final _optionMap = <String, void Function(_NexxPlayPageState)>{
    'Clear Cache': (s) => s._controller?.clearCache(),
    'Play': (s) => s._controller?.play(),
    'Pause': (s) => s._controller?.pause(),
    'Toggle': (s) => s._controller?.toggle(),
    'Mute': (s) => s._controller?.mute(),
    'Unmute': (s) => s._controller?.unmute(),
    'Next': (s) => s._controller?.next(),
    'Previous': (s) => s._controller?.previous(),
    'Seek To 7.5 sec': (s) => s._controller?.seekTo(7500),
    'Seek By 5 sec': (s) => s._controller?.seekBy(5),
    'Swap to position 1': (s) => s._controller?.swapToPosition(1),
    'Get Current Media': (s) async {
      final data = await s._controller?.getCurrentMedia();
      debugPrint("Current Media: $data");
    },
    'Get Current Media Parent': (s) async {
      final data = await s._controller?.getCurrentMediaParent();
      debugPrint("Get Current Media Parent: $data");
    },
    'Get Audio Tracks': (s) async {
      final data = await s._controller?.getAudioTracks();
      debugPrint("Get Audio Tracks: $data");
    },
    'Get Connected Files': (s) async {
      final data = await s._controller?.getConnectedFiles();
      debugPrint("Get Connected Files: $data");
    },
    'Get Current Playback State': (s) async {
      final data = await s._controller?.getCurrentPlaybackState();
      debugPrint("Current Playback State: $data");
    },
    'Get Current Time': (s) async {
      final data = await s._controller?.getCurrentTime();
      debugPrint("Current Time: $data");
    },
    'Is Playing?': (s) async {
      final data = await s._controller?.isPlaying();
      debugPrint("Is Playing?: $data");
    },
    'Is Playing Ad?': (s) async {
      final data = await s._controller?.isPlayingAd();
      debugPrint("Is Playing Ad?: $data");
    },
    'Is Muted?': (s) async {
      final data = await s._controller?.isMuted();
      debugPrint("Is Muted?: $data");
    },
    'Is In PiP?': (s) async {
      final data = await s._controller?.isInPiP();
      debugPrint("Is In PiP?: $data");
    },
    'Is Casting?': (s) async {
      final data = await s._controller?.isCasting();
      debugPrint("Is Casting?: $data");
    },
    'List Local Media': (s) async {
      // final media = await s._controller?.listLocalMedia('#TODO');
      final media = await s._controller?.listLocalMedia();
      debugPrint(media?.isEmpty ?? true
          ? 'Local Media: No media'
          : 'Local Media: ${media!.join(", ")}');
    },
    'Clear Local Media': (s) {
      // s._controller?.clearLocalMedia('#TODO');
      s._controller?.clearLocalMedia();
    },
    'Disk Space Used For Local Media': (s) async {
      final space = await s._controller?.diskSpaceUsedForLocalMedia();
      debugPrint(space == null ? 'Space Used: Unknown' : 'Space used: $space');
    }
    // 'Update Configuration': (s) {
    //   s._controller?.updateConfiguration(key: '#TODO', value: '#TODO');
    // },
    // 'Update Environment': (s) {
    //   s._controller?.updateEnvironment(key: '#TODO', value: '#TODO');
    // },
    // 'Swap To Media Item': (s) {
    //   s._controller?.swapToMediaItem(
    //       mediaID: '#TODO', streamType: '#TODO', startPosition: 1, delay: 1);
    // },
    // 'Swap To Global ID': (s) {
    //   s._controller
    //       ?.swapToGlobalID(globalID: '#TODO', startPosition: 1, delay: 1);
    // },
    // 'Swap To Remote Media': (s) {
    //   s._controller
    //       ?.swapToRemoteMedia(reference: '#TODO', provider: '#TODO',
    //        delay: 1);
    // },
    // 'Start Downloading Local Media': (s) {
    //   s._controller?.startDownloadingLocalMedia(
    //       mediaID: '#TODO', streamType: '#TODO', provider: '#TODO');
    // },
    // 'Has Download Of Local Media': (s) async {
    //   final bool result = await s._controller?.hasDownloadOfLocalMedia(
    //       mediaID: '#TODO', streamType: '#TODO', provider: '#TODO') ?? false;
    //   debugPrint('Has Download of Local Media? $result');
    // },
  };

  static final _modeTransformation =
      <NexxEventType, _PlayerMode Function(_PlayerMode)>{
    NexxEventType.enterFullScreen: (mode) => mode.fullscreen(isEnabled: true),
    NexxEventType.exitFullScreen: (mode) => mode.fullscreen(isEnabled: false),
    NexxEventType.enterPIP: (mode) => mode.pip(isEnabled: true),
    NexxEventType.exitPIP: (mode) => mode.pip(isEnabled: false),
  };

  static const _environment = NexxPlayEnvironment({
    'domain': '#TODO',
    'startFullscreen': 0,
  });

  static const _configuration = NexxPlayConfiguration({
    'dataMode': 'API',
    'exitMode': 'load',
    'streamingFilter': '',
    'adType': 'IMA',
    'autoPlay': 0,
    'autoNext': 1,
    'disableAds': 1,
    'hidePrevNext': 0,
    'forcePrevNext': 0,
    'startPosition': 0,
    'delay': 0.0,
  });
}

@immutable
class _PlayerMode {
  final bool isFullscreen;
  final bool isInPIP;

  bool get shouldExpand => isFullscreen || isInPIP;

  const _PlayerMode({required this.isFullscreen, required this.isInPIP});

  const _PlayerMode.initial() : this(isFullscreen: false, isInPIP: false);

  _PlayerMode fullscreen({required bool isEnabled}) =>
      _PlayerMode(isFullscreen: isEnabled, isInPIP: isInPIP);

  _PlayerMode pip({required bool isEnabled}) =>
      _PlayerMode(isFullscreen: isFullscreen, isInPIP: isEnabled);

  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;
    return other is _PlayerMode &&
        other.isFullscreen == isFullscreen &&
        other.isInPIP == isInPIP;
  }

  @override
  int get hashCode => isFullscreen.hashCode ^ isInPIP.hashCode;

  @override
  String toString() =>
      '_PlayerMode(isFullscreen: $isFullscreen, isInPIP: $isInPIP)';
}

@immutable
class _WidgetPlayerEventVisitor implements PlayerEventVisitor<Widget> {
  const _WidgetPlayerEventVisitor();

  @override
  Widget onPlayerEvent(DirectPlayerEvent event) {
    return _DirectPlayerEventWidget(event: event);
  }

  @override
  Widget onPlayerStateChanged(PlayerStateChangeEvent event) {
    return _PlayerStateEventWidget(event: event);
  }
}

class _PlayerStateEventWidget extends StatelessWidget {
  final PlayerStateChangeEvent event;

  const _PlayerStateEventWidget({
    required this.event,
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ListTile(
      title: const Text('Player State Change'),
      subtitle:
          Text('playWhenReady: ${event.playWhenReady}\nstate: ${event.state}'),
    );
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties.add(DiagnosticsProperty('event', event));
  }
}

class _DirectPlayerEventWidget extends StatelessWidget {
  final DirectPlayerEvent event;

  const _DirectPlayerEventWidget({
    required this.event,
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ListTile(
      title: const Text('Player Event'),
      subtitle: Text(event.properties.entries
          .map((e) => '${e.key}: ${e.value}')
          .join(', ')),
    );
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties.add(DiagnosticsProperty('event', event));
  }
}
