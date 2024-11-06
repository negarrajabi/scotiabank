# GitHub User Viewer Application

This project is an Android app that interacts with the public GitHub API to display information about a specific GitHub user and their repositories. The app is built using **Jetpack Compose** for the UI, showcasing a modern, declarative approach to Android development. It demonstrates the use of an Android architecture pattern, network requests, and a UI that closely replicates the provided design requirements.

---

## Table of Contents
1. [Functional Requirements](#functional-requirements)
2. [Design Requirements](#design-requirements)
3. [Architecture](#architecture)
4. [UI Implementation with Jetpack Compose](#ui-implementation-with-jetpack-compose)
5. [API Endpoints and Data Parsing](#api-endpoints-and-data-parsing)
6. [Testing](#testing)
7. [Installation](#installation)
8. [Future Improvements](#future-improvements)

---

## Functional Requirements
- **User Search**: Users can enter a GitHub user ID and retrieve the user's information.
- **User Information Display**: The app displays the user's avatar and name.
- **Repository List**: The app shows a scrollable list of the user’s public repositories, including each repo’s name and description.
- **Repository Details**: Selecting a repository opens a detail screen that provides additional information.
- **Fork Count Badge** (Senior Role Only): The detail screen displays the total number of forks across all repositories. If this count exceeds 5,000, a star badge (highlighted in red/gold) is displayed.

---

## Design Requirements
The app's design and animations closely replicate those shown in the provided screenshots and video, with an emphasis on consistent UI/UX patterns and smooth interactions. All functional and design requirements are implemented according to the provided specifications.

---

## Architecture
This project uses the **MVVM (Model-View-ViewModel)** architectural pattern to ensure a clear separation of concerns and to make the codebase easily testable and scalable. The MVVM pattern consists of the following layers:

- **Model**: Handles data operations and interacts with the GitHub API.
- **ViewModel**: Manages UI-related data and handles business logic, exposing it to the View.
- **View**: Consists of the UI components that observe data from the ViewModel and display it to the user.

### Benefits of MVVM
- **Testability**: The clear separation of UI logic (in the ViewModel) and business logic (in the Model) facilitates unit testing.
- **Maintainability**: Changes in the UI do not affect the core business logic, making maintenance easier.
- **Reusability**: The ViewModel can be reused across different UI components.

---

## UI Implementation with Jetpack Compose
The entire UI is implemented using **Jetpack Compose**, Android’s modern toolkit for building native UI. With Compose, the app benefits from:
- **Declarative UI**: UI components are defined declaratively, making it easier to visualize and update the UI based on state changes.
- **Simplified Code Structure**: UI and logic are separated in a clean, readable format, aligning well with MVVM principles.
- **Smooth Animations**: Compose provides built-in support for creating smooth and responsive animations, enhancing the user experience.

---

## API Endpoints and Data Parsing
The app communicates with the GitHub API using Retrofit. Only the relevant fields from each endpoint are parsed and displayed.

### Endpoints Used
1. **User Information**: Retrieves the user’s name and avatar.
   - Endpoint: `https://api.github.com/users/{userId}`
   - Required fields:
     ```json
     {
       "name": "String",
       "avatar_url": "String"
     }
     ```

2. **User Repositories**: Retrieves the user's public repositories.
   - Endpoint: `https://api.github.com/users/{userId}/repos`
   - Required fields:
     ```json
     [
       {
         "name": "String",
         "description": "String",
         "updated_at": "String",
         "stargazers_count": Integer,
         "forks": Integer
       }
     ]
     ```

---

## Testing
This project includes both **unit tests** and **UI tests** to ensure the reliability and functionality of the app.

- **Unit Testing**: Tests are written to validate business logic within the ViewModel, including parsing and processing data from the GitHub API.
- **UI Testing**: Using Espresso and Jetpack Compose testing libraries, UI tests verify navigation, data binding, and user interactions across screens.

### Coverage
- User data retrieval and display
- Repository list loading and navigation to details
- Fork count and badge logic (if applicable)

---

## Installation
To set up and run the project locally:
1. **Prerequisites**: Ensure you have the latest version of [Android Studio](https://developer.android.com/studio) and a compatible Android device or emulator.
2. **Clone the Repository**:
   ```bash
   git clone https://github.com/negarrajabi/scotiabank.git
   cd scotiabank
