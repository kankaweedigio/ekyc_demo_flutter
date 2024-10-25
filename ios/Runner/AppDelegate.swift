import Flutter
import UIKit
import EkycFramework


@main
@objc class AppDelegate: FlutterAppDelegate {
    var env: String = "UAT"
    var sessionId: String = NSUUID().uuidString.lowercased()
    var nonProdToken: String = "Token"
    

  override func application(
    _ application: UIApplication,
    didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
  ) -> Bool {
      let controller : FlutterViewController = window?.rootViewController as! FlutterViewController
      let channel = FlutterMethodChannel(name: "com.test.ekyc/initEkyc", binaryMessenger: controller.binaryMessenger)
      
      
      channel.setMethodCallHandler{ (call:FlutterMethodCall,result:@escaping FlutterResult) in
          if call.method == "initEkyc" {
              print(call.method)
              EkycFrameworkKit.initEkyc(sessionId: self.sessionId,
                                        token: "",
                                        environment: self.env,
                                        customizeTheme: nil,
                                        language: nil){
                  success,description,ekycToken in
                  DispatchQueue.main.async {
                                          guard let rootVc = UIApplication.shared.keyWindow?.rootViewController else {
                                              print("No root view controller found")
                                              return
                                          }
                                          
                      self.livenessCheck(rootVc: rootVc, token: self.nonProdToken)
                      
                                      }
                  
                  
              }
              
          }
      }
    
    GeneratedPluginRegistrant.register(with: self)
    return super.application(application, didFinishLaunchingWithOptions: launchOptions)
  }
    
    private func configToken() -> String{
        return nonProdToken
    }
    
    private func livenessCheck(rootVc: UIViewController,token: String?){
        print("livenessCheck")
        let ekycToken = token ?? "(null)"
        print("token: "+ekycToken)
        EkycFrameworkKit.livenessCheck(fromViewController: rootVc){
            success,description in
            let data = "success:" + String(success)
            + "\ndescription:" + description
            + "\nekycToken:" + ekycToken + "\n"
            print(data)
        }
        
    }
    
    
       
    }

