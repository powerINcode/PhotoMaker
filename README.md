# PhotoMaker
## Application that allow you to make a photos and browse them

PhotoMaker is a mobile-ready, offline-storage compatible

## Features
- Browse photo gallery
- Create photo and tag it with name
- Browse particular photo

## Tech

PhotoMaker uses a number of open source projects to work properly:

- Android architecture components 
- RxJava3
- Room
- Dagger 2
- Glide

## Archecture
 Application based on the MVI architecture pattern.
 Project structure contains:
 - **app module** - main module of the application
 - **feature_/featureName/ modules** - modules that describe particular feature
 -- each feature contain **api** and **imlp** modules where **api** is pure java library and contain only public interfaces that feature would to share other modules and **imlp** contain source code of the feature and implementations of the **api** interfaces
-- **Flows** - Dagger component that describe dependencies of the feature and all share data that feature would to share other modules like *UseCase*
- **Core module** - contain all core functionalty that share inside whole app
- **Repositories module** - contain *repositories* contain **api** and **imlp** modules where **api** is pure java library and contain only public interfaces of the repositories and **imlp** contain implementations of the **api** interfaces
- **UseCase** - Entity that evalueate a business logic. It build on the **Command** pattern and contain only one method that should slove a task
- **Repository** - Abstraction for the data layer
