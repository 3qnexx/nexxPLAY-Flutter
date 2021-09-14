import 'package:flutter/material.dart';
import 'package:nexx/nexx.dart';

void main() => runApp(NexxExampleApp());

class NexxExampleApp extends StatefulWidget {
  @override
  _NexxExampleAppState createState() => _NexxExampleAppState();
}

class _NexxExampleAppState extends State<NexxExampleApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: const Text('Nexx Player example app')),
        body: Center(child: NexxPlayer(configuration: _configuration)),
      ),
    );
  }

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
