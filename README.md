# What's This Repository
This repository is for my Jetpack Compose training.

# What's GithubWatcher
## Search Github User
Show github users list when set keyword and tap search button.
![User List](./doc/ss1.jpg)

## User Detail and Repositories
Show user detail and unforked repositories that tapped user on previous screen.
![User Description](./doc/ss2.jpg)

## Repository
Open Repository page in Github that tapped repository on previous screen.
![Repository](./doc/ss3.jpg)

# Directory
```
├── app Applications root directory  
│   └── src Source code  
│       ├── androidTest not implemented yet.  
│       ├── main 
│       │   ├── java
│       │   │   ├── data define model layer.
│       │   │   │   ├── di Inject repository.
│       │   │   │   ├── model Define data class.
│       │   │   │   └── repository Define method for data.
│       │   │   ├── event Event call from ui to viewmodel.
│       │   │   ├── network.
│       │   │   │   ├── di Inject netwrok module.
│       │   │   │   ├── models Define data class with outside API(Github).
│       │   │   │   └── retrofit Define network client.
│       │   │   └── ui Define compose.
│       │   │       └── Almost default.
│       │   └── res Use icon and strings.
│       └── test/java/org/firespeed/githubwatcher Unit test only few object yet.
├── gradle spec version of libraries.
└── doc for documents
```
# Introduce
First of all should add row API_TOKEN = "<YOUR_GITHUB_API_TOKEN>" to `local.properties`.

# LICENSE
This repository is  distributed under the terms of the Apache License 2.0.
See the [LICENSE](LICENSE).