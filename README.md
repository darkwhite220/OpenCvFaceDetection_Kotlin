## Face Detection (Compose, Video in device, OpenCV)

Sample app to demonstrate [OpenCV Face detection](https://opencv.org/) with video from device in Compose.

## Benchmark 🤓

On my test device Samsung M21:

- Extracting frame avg. 20ms
- Face Detecting:
  - 1920*1080: 400ms
  - 640*360: 75ms
- Converting the frame to bitmap avg. 8ms

## Setup

1. Download the "Android" package from [OpenCV](https://opencv.org/releases/)
2. Extract the "sdk" folder & copy it into root directory of your project
3. File -> New -> Import module -> Select the sdk module & name it ":openCV"
*You may get errors here, just follow the steps here*
4. Update "settings.gradle" file
    <details>
    <summary>settings.gradle</summary>  
    <p>
    pluginManagement {
        repositories {
            google {
                content {
                    includeGroupByRegex("com\\.android.*")
                    includeGroupByRegex("com\\.google.*")
                    includeGroupByRegex("androidx.*")
                }
            }
            mavenCentral()
            gradlePluginPortal()
        }
    }
    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            google()
            mavenCentral()
        }
    }
    
    rootProject.name = "OpenCVFaceDetection" <---- YOUR PROJECT NAME
    include(":app")
    
    include(":openCV")
    project(":openCV").projectDir = File(rootDir, "sdk/")
    </p>
  </details>
    5. Update "build.gradle (Module :openCV)" file
    <details>
  <summary>build.gradle (Module :openCV)</summary>  
<p>
  ...
  minSdkVersion 24 (same as your main module)
  targetSdkVersion 34 (same as your main module)
  add:
  kotlinOptions {
    jvmTarget = "1.8" (same as your main module)
  }
  ...
</p>
</details>
    6. Import openCV in your mai module
    <details>
  <summary>build.gradle (Module :app)</summary>
<p>
  {
    ...
    implementation (fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation (project(":openCV"))
  }
</p>
</details>
    7. Download the latest version of onnx file for face detection and place it in res/raw
[Current latest face_detection_yunet_2023mar.onnx](https://github.com/opencv/opencv_zoo/tree/main/models/face_detection_yunet)
*Note: don't pick the file with "_int8.onnx" ending*

## Find me! 👨‍💻

You need custom made app/feature?

<p>
<a href="https://www.upwork.com/workwith/abderrahima" target="_blank"><img src="https://upload.wikimedia.org/wikipedia/commons/f/f4/Upwork_Logo.svg" alt="Upwork" style="height: 60px !important;width: 223px !important;" ></a>
</p>

## Support me! ❤️

If you like my work please support me, Thank you.

<p>
<img src="art/bmc_qr.png" width=120px height=120px>
<a href="https://www.buymeacoffee.com/darkwhiteapps" target="_blank"><img src="https://cdn.buymeacoffee.com/buttons/v2/default-yellow.png" alt="Buy Me A Coffee" style="height: 60px !important;width: 217px !important;" ></a>

<img src="art/ko-fi_qrcode.png" width=120px height=120px>
 <a href='https://ko-fi.com/darkwhite' target='_blank'><img style='border:0px;height:60px;width: 170px;' src='https://storage.ko-fi.com/cdn/brandasset/kofi_bg_tag_dark.png' alt='Buy Me a Coffee at ko-fi.com' /></a>
</p>

## License

```
MIT License

Copyright (c) 2023 DarkWhite220

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```