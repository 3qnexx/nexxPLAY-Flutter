import 'package:flutter/foundation.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/services.dart';
import 'package:nexx/src/configuration.dart';
import 'package:nexx/src/controller.dart';

class NexxPlayer extends StatefulWidget {
  final NexxPlayerConfiguration configuration;
  final ValueSetter<NexxPlayerController> onControllerCreated;

  NexxPlayer({
    required this.configuration,
    required this.onControllerCreated,
  }) : super(key: ValueKey(configuration));

  @override
  _NexxPlayerState createState() => _NexxPlayerState();

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('configuration', configuration))
      ..add(ObjectFlagProperty.has('onControllerCreated', onControllerCreated));
  }
}

class _NexxPlayerState extends State<NexxPlayer> {
  late NexxPlayerController _controller;

  @override
  Widget build(BuildContext context) {
    return PlatformViewLink(
      viewType: _viewType,
      surfaceFactory: (context, PlatformViewController controller) {
        return AndroidViewSurface(
          controller: controller as AndroidViewController,
          gestureRecognizers: const <Factory<OneSequenceGestureRecognizer>>{},
          hitTestBehavior: PlatformViewHitTestBehavior.opaque,
        );
      },
      onCreatePlatformView: (PlatformViewCreationParams params) {
        return PlatformViewsService.initSurfaceAndroidView(
          id: params.id,
          viewType: _viewType,
          layoutDirection: TextDirection.ltr,
          creationParams: widget.configuration.toMap(),
          creationParamsCodec: const StandardMessageCodec(),
        )
          ..addOnPlatformViewCreatedListener((int id) {
            params.onPlatformViewCreated(id);
            _onPlatformViewCreated(id);
          })
          ..create();
      },
    );
  }

  void _onPlatformViewCreated(int id) {
    _controller = NexxPlayerControllerFactory().create(_viewType, id);
    widget.onControllerCreated(_controller);
  }

  static const _viewType = 'tv.nexx.flutter.android';
}
