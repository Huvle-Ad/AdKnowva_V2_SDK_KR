import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);


  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Adknowva Sample',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Adknowva Sample'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const _channel = MethodChannel('com.example.adknowva/ads');

  // late final MethodChannel _channel;


  Future<void> _loadAd() async {
    try {
      await _channel.invokeMethod('loadAd');
    } on PlatformException catch (e) {
      print(e.message);
    }
  }

  Future<void> _interstitialAd() async {
    try {
      await _channel.invokeMethod('interstitialAd');
    } on PlatformException catch (e) {
      print(e.message);
    }
  }

  @override
  void initState() {
    super.initState();
    _loadAd();
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            ElevatedButton(
              child: const Text('전면 광고'),
              onPressed: () {
                _interstitialAd();// 클릭시 실행할 코드
              },
            ),
          ],
        ),
      ),
    );
  }
}
