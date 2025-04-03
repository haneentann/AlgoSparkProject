Project: AlgoSpark Mobile - Interactive Programming Exercises

Description:
This Android application provides interactive programming exercises for high school students, focusing on Java. 
It utilizes the Gemini API for dynamic exercise generation and code analysis, and also offers the option to load exercises from an image ana analyze it.

Features:

-   **Dynamic Exercise Generation:**
    -      Users can select a programming subject from a dropdown menu.
    -      The Gemini API is used to generate exercises on demand, including problem descriptions, code templates, and correct answers.
    -      This allows for a dynamic and adaptable learning experience.
-   **Code Submission and Analysis:**
    -      Users can write and submit their code solutions within the app.
    -      The Gemini API analyzes the submitted code and provides feedback on its correctness and efficiency.
-   **Hints and Answer Display:**
    -      Users can request hints to guide them towards the solution.
    -      The correct answer can be displayed in a dialog box for reference.
-   **Subject Selection:**
    -   A spinner allows the user to choose the topic that they want to practice.
Files:

-   `ExerciseActivity.java`: Contains the main logic for the exercise activity, including API calls, JSON parsing, and UI updates.
-   `activity_exercise.xml`: Defines the layout for the exercise activity, including UI elements like EditText, TextView, and Button.
-   `Exercise.java`: Represents the data model for programming exercises.
-   `exercises.json`: Contains a list of pre-defined exercises in JSON format (used for local loading).
-   `GeminiManager.java`: Manages the interaction with the Gemini API.
-   `GeminiCallback.java`: Defines the callback interface for Gemini API responses.
-   `R.array.subjects_array`: contains the list of subjects that the user can choose from.

Setup:

1.  **Android Studio:** Ensure you have Android Studio installed.
2.  **Gemini API Key:** Obtain a valid Gemini API key and configure it in `GeminiManager.java`.
3.  **Dependencies:** Ensure all Gradle dependencies (Gson, etc.) are correctly installed.
5.  **Run:** Build and run the application on an Android emulator or device.

Usage:

1.  Select the either to generate excercise or analyze a given code from file/image.
2.  Choose a programming subject from the dropdown menu.
3.  Read the exercise description and write your code in the provided EditText.
4.  Click the "Submit" button to analyze your code.
5.  Click the "Hint" button to get a hint.
6.  Click the "Answer" button to view the correct answer.
7.  View the feedback provided by the Gemini API.

Notes:

-   The application requires an internet connection to use the Gemini API.
-   The application should be learnning model but for now it's static.
-   Error handling and UI improvements can be implemented for a more robust application.
