import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

abstract class NexxPlayerControllerFactory {
  factory NexxPlayerControllerFactory() =
      _MethodChannelNexxPlayerControllerFactory;

  NexxPlayerController create(String type, int id);
}

abstract class NexxPlayerController {
  Future<bool> start();
}

class _MethodChannelNexxPlayerControllerFactory
    implements NexxPlayerControllerFactory {
  const _MethodChannelNexxPlayerControllerFactory();

  @override
  NexxPlayerController create(String type, int id) {
    final channel = MethodChannel('${type}_$id');
    return _MethodChannelNexxPlayerController(channel);
  }
}

class _MethodChannelNexxPlayerController implements NexxPlayerController {
  _MethodChannelNexxPlayerController(this._channel);

  @override
  Future<bool> start() async {
    final result = await _channel.invokeMapMethod<String, Object>('start');
    if (result == null) throw ArgumentError.notNull('result');
    final typed = _StartResult(_MethodChannelMapResult(result));
    return typed.isSuccessful();
  }

  final MethodChannel _channel;
}

class _MethodChannelMapResult {
  final Map<String, Object> _raw;

  const _MethodChannelMapResult(this._raw);

  Map<String, Object> raw() => _raw;

  bool isArgumentPresent(String name, Type type) {
    return _raw[name]?.runtimeType == type;
  }

  @override
  String toString() => '_MethodChannelMapResult(_raw: $_raw)';
}

class _StartResult {
  final Map<String, Object> _raw;

  const _StartResult._(this._raw);

  factory _StartResult(_MethodChannelMapResult result) {
    if (!result.isArgumentPresent('id', int)) {
      throw ArgumentError.value(result, 'result', 'Result does not contain ID');
    }
    if (!result.isArgumentPresent('started', bool)) {
      throw ArgumentError.value(
          result, 'result', 'Result does not contain success flag');
    }
    return _StartResult._(result.raw());
  }

  bool isSuccessful() {
    return _raw['started'] as bool;
  }

  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;
    return other is _StartResult && mapEquals(other._raw, _raw);
  }

  @override
  int get hashCode => _raw.hashCode;

  @override
  String toString() => '_StartResult(_raw: $_raw)';
}
