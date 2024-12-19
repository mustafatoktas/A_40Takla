<h1 align="center">
40 Takla<a name="readme-top"></a>
</h1>

<div align="center">
  <img src="../../Readme Resources/40 Takla Logo.png" alt="Logo" width="120"/>
</div>

<div align="right">
  <a href="../../Readme.md" target="_blank">Türkçe</a> | English
</div>

## Contents  

- [About the Application](#about-the-application)
- [Screenshots](#screenshots)
- [Architecture, Technology and Libraries Used](#architecture-technology-and-libraries-used)
- [Layers](#layers)
- [Tested Versions](#tested-versions)
- [Running the Application](#running-the-application)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)


![-----------------------------------------------------](../../Readme%20Resources/Çizgi.png)

## About the Application

<table>
  <tr>
    <th style="text-align: left; font-weight: bold;">Operating System</th>
    <td style="text-align: left;">Android</td>
  </tr>
  <tr>
    <th style="text-align: left; font-weight: bold;">App Type</th>
    <td style="text-align: left;">Mobile</td>
  </tr>
  <tr>
    <th style="text-align: left; font-weight: bold;">Permissions Used</th>
    <td style="text-align: left;">INTERNET<br>VIBRATE</td>
  </tr>
</table>

<br>

With this application, you can make the screen flip with every tap and receive a fun message after 40 flips.
You can also add your name to the "40 Flips Achievers" list and view all registered users.


![-----------------------------------------------------](../../Readme%20Resources/Çizgi.png)

## Screenshots

Screenshots are taken from the `v1.0.0` version of the application. The UI may be fully or partially changed in newer versions.

<details>
  <summary>Main Screen</summary>
  
  | ![ScreenShot 1](../../Readme%20Resources/Ekran%20Görüntüleri/Main%201.png) | ![ScreenShot 2](../../Readme%20Resources/Ekran%20Görüntüleri/Main%202.png) |
  | -------------------------------------------------------------------------- | -------------------------------------------------------------------------- |
  
</details>

<details>
  <summary>Congratulation Screen</summary>
  
  | ![ScreenShot 3](../../Readme%20Resources/Ekran%20Görüntüleri/Cong%201.png) | ![ScreenShot 4](../../Readme%20Resources/Ekran%20Görüntüleri/Cong%202.png) | ![ScreenShot 5](../../Readme%20Resources/Ekran%20Görüntüleri/Cong%203.png) |
  | -------------------------------------------------------------------------- | -------------------------------------------------------------------------- | -------------------------------------------------------------------------- |
  | ![ScreenShot 6](../../Readme%20Resources/Ekran%20Görüntüleri/Cong%204.png) |                                                                            |                                                                            |
  
</details> 

<details>
  <summary>List Screen</summary>
  
  | ![ScreenShot 7](../../Readme%20Resources/Ekran%20Görüntüleri/List%201.png) | ![ScreenShot 8](../../Readme%20Resources/Ekran%20Görüntüleri/List%202.png) |
  | -------------------------------------------------------------------------- | -------------------------------------------------------------------------- |
  
</details>   

<details>
  <summary>About Screen</summary>
  
  | ![ScreenShot 9](../../Readme%20Resources/Ekran%20Görüntüleri/About%201.png) | ![ScreenShot 10](../../Readme%20Resources/Ekran%20Görüntüleri/About%202.png) |
  | --------------------------------------------------------------------------- | ---------------------------------------------------------------------------- |
  
</details>   


![-----------------------------------------------------](../../Readme%20Resources/Çizgi.png)

## Architecture, Technology and Libraries Used

- `Multi-Module`
- `MVVM + Clean Architecture`
- `Jetpack Compose`
- `Android Jetpack` 
  - Type Safe Navigation
  - Flow
  - Coroutines
  - Lifecycle
  - KSP
  - Hilt
  - Serialization
  - Splash Screen
  - Material Design
- `Detekt`
- `Firebase`
- `Coil`
- `Sonner`
- `Konfetti`
- `PullRefresh`

[libs.versions.toml](../../40TaklaApp/gradle/libs.versions.toml)

[**Kök Dizin** build.gradle.kts](../../40TaklaApp/build.gradle.kts)

[**App Module** build.gradle.kts](../../40TaklaApp/app/build.gradle.kts)

[**core-data** build.gradle.kts](../../40TaklaApp/core/core-data/build.gradle.kts)

[**core-domain** build.gradle.kts](../../40TaklaApp/core/core-domain/build.gradle.kts)

[**core-common** build.gradle.kts](../../40TaklaApp/core/core-common/build.gradle.kts)

[**core-ui** build.gradle.kts](../../40TaklaApp/core/core-ui/build.gradle.kts)

[**usecase-app** build.gradle.kts](../../40TaklaApp/usecase/usecase-app/build.gradle.kts)

[**use-firebase** build.gradle.kts](../../40TaklaApp/usecase/usecase-firebase/build.gradle.kts)

[**feat-main** build.gradle.kts](../../40TaklaApp/feat/feat-main/build.gradle.kts)

[**feat-congratulation** build.gradle.kts](../../40TaklaApp/feat/feat-congratulation/build.gradle.kts)

[**feat-list** build.gradle.kts](../../40TaklaApp/feat/feat-list/build.gradle.kts)

[**feat-about** build.gradle.kts](../../40TaklaApp/feat/feat-about/build.gradle.kts)

![MVVM Mimari Yapısı](../../Readme%20Resources/Mimari/MVVM.png)


![-----------------------------------------------------](../../Readme%20Resources/Çizgi.png)

## Layers

<table>
  <tr>
    <th style="text-align: center;">Data Layer</th>
    <th style="text-align: center;">Domain Layer</th>
    <th style="text-align: center;">UI Layer</th>
  </tr>
  <tr>
    <td style="text-align: center;">Data Management</td>
    <td style="text-align: center;">Business Logic</td>
    <td style="text-align: center;">User Interface</td>
  </tr>
  <tr>
    <td style="text-align: center;">Firebase<br>Dependency Injection<br>Repository Impl</td>
    <td style="text-align: center;">Use Case<br>Repository<br>Model</td>
    <td style="text-align: center;">Compose<br>ViewModel<br>Ui State<br>Ui Action<br>Ui Effect</td>
  </tr>
</table>


![-----------------------------------------------------](../../Readme%20Resources/Çizgi.png)

## Tested Versions

<table>
  <tr>
    <td style="text-align: left;">Android 8.1 Oreo</td>
    <td style="text-align: left;">API 27</td>
    <td style="text-align: left;">✅️</td>
  </tr>
  <tr>
    <td style="text-align: left;">Android 9 Pie</td>
    <td style="text-align: left;">API 28</td>
    <td style="text-align: left;">✅️</td>
  </tr>
  <tr>
    <td style="text-align: left;">Android 10</td>
    <td style="text-align: left;">API 29</td>
    <td style="text-align: left;">✅️</td>
  </tr>
  <tr>
    <td style="text-align: left;">Android 11</td>
    <td style="text-align: left;">API 30</td>
    <td style="text-align: left;">✅️</td>
  </tr>
  <tr>
    <td style="text-align: left;">Android 12</td>
    <td style="text-align: left;">API 31</td>
    <td style="text-align: left;">✅️</td>
  </tr>
  <tr>
    <td style="text-align: left;">Android 12L</td>
    <td style="text-align: left;">API 32</td>
    <td style="text-align: left;">✅️</td>
  </tr>
  <tr>
    <td style="text-align: left;">Android 13</td>
    <td style="text-align: left;">API 33</td>
    <td style="text-align: left;">✅️</td>
  </tr>
  <tr>
    <td style="text-align: left;">Android 14</td>
    <td style="text-align: left;">API 34</td>
    <td style="text-align: left;">✅️</td>
  </tr>
  <tr>
    <td style="text-align: left;">Android 15</td>
    <td style="text-align: left;">API 35</td>
    <td style="text-align: left;">✅️</td>
  </tr>
</table>


![-----------------------------------------------------](../../Readme%20Resources/Çizgi.png)

## Running the Application

- To download the project files to your computer, review the code or [contribute](#contributing) under the
  [license rights](https://www.gnu.org/licenses/gpl-3.0.html), go to the directory where you want to download the project
  on a computer with [git](https://git-scm.com) installed and run the following command in the terminal:
  ```
  git clone https://github.com/mustafatoktas/A_40Takla.git
  ```

- You can visit the [releases](https://github.com/mustafatoktas/A_40Takla/releases) page to download the latest version of the application.


![-----------------------------------------------------](../../Readme%20Resources/Çizgi.png)

## Contributing

Contribution rules and steps for those who want to contribute to the project are explained in the [contributing file](./Contributing.en.md).


![-----------------------------------------------------](../../Readme%20Resources/Çizgi.png)

<div align="center">
  <a href="https://github.com/mustafatoktas/W.BE_RepoVisitorCounterAPI" target="_blank"> <img src="https://toktasoft.com/api/github2/repo-visitor-counter.php?repo=fka28jtq9ezmyu7&show_repo_name=1&show_date=1&show_brand=0&txt_color=209,215,224&bg_color=45,52,58" alt="Repo Visitor Counter"/> </a>
</div>

<br>
  
<div align="center">
  <a href="https://buymeacoffee.com/mustafatoktas" target="_blank"> <img src="../../Readme Resources/İletişim/Buy Me a Coffee.png" alt="Buy Me a Coffee" height="64"/> </a>
</div>


![-----------------------------------------------------](../../Readme%20Resources/Çizgi.png)

## License

```
Copyright 2024 Mustafa TOKTAŞ

Licensed under the GNU General Public License v3.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.gnu.org/licenses/gpl-3.0.html

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


![-----------------------------------------------------](../../Readme%20Resources/Çizgi.png)

## Contact

<a href="mailto:info@mustafatoktas.com"              target="_blank"> <img src="../../Readme Resources/İletişim/Mail.png"     alt="Mail"     width="64"/> </a>
<a href="https://t.me/mustafatoktas00"               target="_blank"> <img src="../../Readme Resources/İletişim/Telegram.png" alt="Telegram" width="64"/> </a>
<a href="https://www.linkedin.com/in/mustafatoktas/" target="_blank"> <img src="../../Readme Resources/İletişim/LinkedIn.png" alt="LinkedIn" width="64"/> </a>

<p align="center">
  <a href="#readme-top"> <img src="../../Readme Resources/Back to Top.png" alt="Back to Top" height="64"/> </a>
</p>
