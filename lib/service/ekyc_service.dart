import 'package:flutter/services.dart';

class EkycService {
  static const platform = MethodChannel('com.test.ekyc/initEkyc');

  void startEkyc(String sessionId, String token) async {
    try {
      final result = await platform.invokeMethod('initEkyc', {
        'sessionId': sessionId,
        'token': token,
      });
      print(result);
    } on PlatformException catch (e) {
      print("Failed to start EKYC: '${e.message}'.");
    }
  }

  void startLivenessCheck() async {
    try {
      final result = await platform.invokeMethod('livenessCheck');
      print(result);
    } on PlatformException catch (e) {
      print("Failed to start liveness check: '${e.message}'.");
    }
  }
}
