# Active Life - Fitness Management App

Active Life is a comprehensive health and fitness app developed using Android Studio. It provides users with a dynamic and user-friendly platform to manage their fitness goals, track progress, and stay motivated. The app features personalized workout routines, meal plans, progress tracking, and much more to help users maintain a healthier lifestyle.

## Features
- **Splash Screen**: Displays a random motivational quote and key metrics like streak count and step count.
- **User Sign-Up & Login**: Users can create accounts and log in securely.
- **Homepage**: Displays the user’s name, avatar, daily streaks, step count, workout progress, and recommended workouts.
- **Meal Planning**: Curated meal plans with detailed nutritional information and tips for healthy eating.
- **Workouts**: A library of workout routines with animated video guides, step-by-step instructions, and timers.
- **Profile**: Users can manage personal information (weight, height, age), upload progress photos, track achievements, and calculate BMI.
- **Motivational Tools**: Daily streak tracking, achievement badges, and progress photos to keep users engaged.

## Problem Definition
Maintaining a healthy lifestyle can be challenging without structured guidance and motivation. Existing fitness solutions are often fragmented, making it difficult for users to stay consistent. Active Life integrates workout tracking, meal planning, and personal progress in a single platform to help users stay on track.

## Project Category
- Health and Fitness Applications
- Mobile App Development

## Objectives
- **User-Friendly Interface**: Easy navigation for users of all fitness levels.
- **Personalized Meal Plans & Workouts**: Tailored to meet individual needs and goals.
- **Motivation and Progress Tracking**: Using badges, streaks, and progress photos.
- **Community Building**: Users can share goals and achievements.

## Technologies Used
- **Programming Languages**: Java, XML
- **IDE**: Android Studio
- **Database**: SQLite
- **Design Tools**: Figma (for wireframes and mockups)

## System Design
The app follows Object-Oriented Design (OOD) principles, using concepts like:

- **Encapsulation**: Separate objects for each feature (workouts, meal plans, etc.)
- **Abstraction**: Hiding complex technical details from the user interface.
- **Inheritance**: Shared features between workout types to minimize redundancy.
- **Polymorphism**: Flexibility to handle different types of workouts or meal plans under one system.

Data Flow Diagrams (DFDs), Entity-Relationship Diagrams (ERDs), Use Case Diagrams, and Class Diagrams were used to design and organize the app’s architecture.

## Requirements
### Functional Requirements
- User Registration & Login
- Dashboard Display (streaks, step counts, BMI, progress)
- Workout Library with Instructions
- Meal Planning Section
- Progress Tracking (photos, achievements, badges)
- Interactive Workouts (start, pause, skip, quit)

### Non-Functional Requirements
- **Usability**: Intuitive and user-friendly interface.
- **Performance**: Fast loading times and smooth operation.
- **Scalability**: The app can handle increasing user data.
- **Security**: User data is encrypted for privacy.
- **Compatibility**: Works on Android and iOS devices.

## Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/your-username/Active-Life.git
    ```
2. Open the project in Android Studio.
3. Build and run the project on an emulator or physical device.

## License
This project is licensed under the MIT License - see the LICENSE file for details.
