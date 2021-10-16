import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:nexx/nexx.dart';

void main() => runApp(const NexxExampleApp());

class NexxExampleApp extends StatelessWidget {
  const NexxExampleApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(home: NavigationPage());
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
              MaterialPageRoute(builder: (_) => const _NexxPlayerPage()),
            );
          },
          child: const Text('Launch player'),
        ),
      ),
    );
  }
}

class _NexxPlayerPage extends StatefulWidget {
  const _NexxPlayerPage({Key? key}) : super(key: key);

  @override
  _NexxPlayerPageState createState() => _NexxPlayerPageState();
}

class _NexxPlayerPageState extends State<_NexxPlayerPage>
    with AdHocVisitor<void> {
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
        appBar: AppBar(title: const Text('Nexx Player example app')),
        body: Center(child: _buildContent()),
      ),
    );
  }

  Widget _buildContent() {
    return Column(
      children: [
        Expanded(child: _buildPlayer()),
        Expanded(child: _buildEventsList()),
      ],
    );
  }

  Widget _buildPlayer() {
    return NexxPlayer(
      key: _playerKey,
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

  Future<void> _startPlayer(NexxPlayerController controller) async {
    try {
      final result = await controller.start();
      if (!mounted) return;
      if (result) {
        _subscribe(controller);
        _controller = controller;
      } else {
        _report('Nexx: player start was not successful');
      }
    } on Object catch (e, st) {
      _report('Nexx: exception occurred during player start: \n$e\n$st');
    }
  }

  void _report(String message) {
    debugPrint(message);
    _messengerKey.currentState?.showSnackBar(SnackBar(content: Text(message)));
  }

  void _subscribe(NexxPlayerController controller) {
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
    super.dispose();
    _subscription?.cancel();
    _subscription = null;
    _controller?.dispose();
    _controller = null;
  }

  NexxPlayerController? _controller;
  StreamSubscription<PlayerEvent>? _subscription;
  _PlayerMode _mode = const _PlayerMode.initial();
  final List<PlayerEvent> _events = [];
  final _playerKey = GlobalKey<NexxPlayerState>();
  final _messengerKey = GlobalKey<ScaffoldMessengerState>();

  static final _modeTransformation =
      <NexxEventType, _PlayerMode Function(_PlayerMode)>{
    NexxEventType.enterFullScreen: (mode) => mode.fullscreen(isEnabled: true),
    NexxEventType.exitFullScreen: (mode) => mode.fullscreen(isEnabled: false),
    NexxEventType.enterPIP: (mode) => mode.pip(isEnabled: true),
    NexxEventType.exitPIP: (mode) => mode.pip(isEnabled: false),
  };

  static final _configuration = NexxPlayerConfiguration(
    provider: '3q',
    domainID: '484',
    mediaID: '1472879',
    playMode: 'video',
    dataMode: 'API',
    exitMode: 'load',
    mediaSourceType: 'NORMAL',
    streamingFilter: '',
    adType: 'IMA',
    autoplay: false,
    autoNext: true,
    disableAds: true,
    hidePrevNext: false,
    forcePrevNext: false,
    startFullScreen: false,
    startPosition: 0,
    delay: 0.0,
  );
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
