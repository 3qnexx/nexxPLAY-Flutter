import 'package:flutter/foundation.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/services.dart';
import 'package:nexxplay/src/configuration.dart';
import 'package:nexxplay/src/controller.dart';

class NexxPlay extends StatefulWidget {
  final NexxPlayEnvironment environment;
  final NexxPlayConfiguration configuration;
  final ValueSetter<NexxPlayController> onControllerCreated;

  const NexxPlay({
    required this.environment,
    required this.configuration,
    required this.onControllerCreated,
    Key? key,
  }) : super(key: key);

  @override
  NexxPlayState createState() => NexxPlayState();

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('environment', environment))
      ..add(DiagnosticsProperty('configuration', configuration))
      ..add(ObjectFlagProperty.has('onControllerCreated', onControllerCreated));
  }
}

class NexxPlayState extends State<NexxPlay> {
  late NexxPlayController _controller;

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
          creationParams: <String, Object>{
            'environment': widget.environment.asMap(),
            'configuration': widget.configuration.asMap(),
          },
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
    _controller = NexxPlayControllerFactory().create(_viewType, id);
    widget.onControllerCreated(_controller);
  }

  static const _viewType = 'tv.nexx.flutter.android';
}
