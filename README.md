# GitHub User Viewer Android Application

This project is an Android app that interacts with the public GitHub API to display information about a specific GitHub user and their repositories. Built with **Jetpack Compose** for the UI, the app leverages modern Android development practices, including dependency injection, CI/CD integration, and test automation.

---

## Table of Contents
1. [Functional Requirements](#functional-requirements)
2. [Design Requirements](#design-requirements)
3. [Architecture](#architecture)
4. [Dependency Injection with Koin](#dependency-injection-with-koin)
5. [CI/CD Workflow](#ci-cd-workflow)
6. [UI Implementation with Jetpack Compose](#ui-implementation-with-jetpack-compose)
7. [API Endpoints and Data Parsing](#api-endpoints-and-data-parsing)
8. [Testing](#testing)
9. [Installation](#installation)
10. [Future Improvements](#future-improvements)

---

## Functional Requirements
- **User Search**: Users can enter a GitHub user ID and retrieve the user's information.
- **User Information Display**: The app displays the user's avatar and name.
- **Repository List**: The app shows a scrollable list of the user’s public repositories, including each repo’s name and description.
- **Repository Details**: Selecting a repository opens a detail screen with additional information.
- **Fork Count Badge** (Senior Role Only): The detail screen displays the total number of forks across all repositories. If this count exceeds 5,000, a star badge (highlighted in red/gold) is displayed.

---

## Design Requirements
The app's design and animations replicate the provided screenshots and video, with a focus on UI/UX consistency and smooth interactions. All requirements have been implemented as specified.

---

## Architecture
The project follows the **MVVM (Model-View-ViewModel)** architecture pattern for a clear separation of concerns, enhanced testability, and scalability. It consists of:

- **Model**: Handles data operations and network requests to the GitHub API.
- **ViewModel**: Manages UI-related data and interacts with the Model layer. The project includes two ViewModels:
  - `UserViewModel`: Manages data for displaying user information and repositories.
  - `RepoDetailViewModel`: Manages data specific to the selected repository's details.
- **View**: Built with Jetpack Compose, displaying the data and handling user interactions in a declarative style.

### DataSource
A shared **DataSource** component handles data retrieval and sharing between `UserViewModel` and `RepoDetailViewModel`, allowing for efficient data flow and ensuring consistency across ViewModels.

### Benefits of MVVM
- **Testability**: The separation of UI logic in ViewModels facilitates unit testing.
- **Maintainability**: Core business logic remains unaffected by UI changes.
- **Reusability**: The ViewModel layer can be reused across multiple UI components.

---

## Dependency Injection with Koin
The app uses **Koin** for dependency injection, providing a clean, modular structure for managing dependencies. Koin is lightweight and fits well with the MVVM pattern, making the codebase easy to configure and test. Using Koin, we inject dependencies like repositories, network services, and ViewModels seamlessly across the app.

---

## CI/CD Workflow
This project includes a **CI/CD workflow** set up through GitHub Actions. The workflow is configured to:
1. Run automated tests, including unit and UI tests, on each push to the repository.
2. Verify code quality, ensuring it meets set standards and passes all required checks before merging.

With CI/CD, we maintain a high level of code quality and quickly identify any issues, improving the development process efficiency.

---

## UI Implementation with Jetpack Compose
The app's UI is fully implemented using **Jetpack Compose**. With Compose, the app benefits from:
- **Declarative UI**: Easy-to-read, state-driven UI components.
- **Simplified Code Structure**: Cleaner, more maintainable code, aligning well with MVVM principles.
- **Smooth Animations**: Compose provides powerful tools for creating seamless animations, enhancing the user experience.

---

## API Endpoints and Data Parsing
The app communicates with the GitHub API using Retrofit. Only specific fields from each endpoint are parsed and displayed.

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
The app includes both **unit tests** and **UI tests** to ensure reliable functionality and a smooth user experience.

- **Unit Testing**: Tests validate business logic within the ViewModels, such as data retrieval and processing.
- **UI Testing**: Using Espresso and Jetpack Compose testing libraries, UI tests verify navigation, data binding, and interactions across screens.

### Test Coverage
- User data retrieval and display.
- Repository list loading and navigation to details.
- Fork count and badge logic.

#### GIF of Running Espresso Test
Below is a GIF showing the automated UI test (Espresso) in action:

![Espresso Test GIF](assets/espressoTest.gif)

---

## Installation
To set up and run the project locally:
1. **Prerequisites**: Install the latest version of [Android Studio](https://developer.android.com/studio) and ensure you have an Android device or emulator available.
2. **Clone the Repository**:
   ```bash
   git clone https://github.com/negarrajabi/scotiabank.git
   cd scotiabank
