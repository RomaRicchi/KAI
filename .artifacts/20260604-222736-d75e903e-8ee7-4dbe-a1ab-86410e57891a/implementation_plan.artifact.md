# Implementation Plan - Tu Kai Screen with Radar Chart

This plan outlines the steps to implement the "Tu Kai" screen using MVVM, including a custom Radar Chart view to display Kai's attributes.

## User Review Required

> [!IMPORTANT]
> - The XP values per category are currently not provided by a specific "Tu Kai" API endpoint. I will implement a mockup in the `TuKaiViewModel` or calculate them from `HabitsViewResponse` if possible.
> - The circular progress bar for energy will be implemented using a custom drawable to match the design in the image.

## Proposed Changes

### 1. Data Layer

#### [NEW] [KaiRepository.java](file:///D:/Documents/Desarrollo de Software/Tercer año/Moviles/AndroidStudioProjects/KAI/app/src/main/java/com/roma/kai/data/repository/KaiRepository.java)
- Create a repository to fetch Kai's status and attribute data.
- It will use `apiService.getHomeView()` and `apiService.getHabitsView()`.

### 2. UI Components (Custom Views)

#### [NEW] [RadarChartView.java](file:///D:/Documents/Desarrollo de Software/Tercer año/Moviles/AndroidStudioProjects/KAI/app/src/main/java/com/roma/kai/ui/custom/RadarChartView.java)
- A custom view that draws a radar chart with 5 axes (Sabiduría, Curiosidad, Cuidado Físico, Creatividad, Alegría).

#### [NEW] [circular_progress_ring.xml](file:///D:/Documents/Desarrollo de Software/Tercer año/Moviles/AndroidStudioProjects/KAI/app/src/main/res/drawable/circular_progress_ring.xml)
- A drawable for the donut-style progress bar.

### 3. Tu Kai Feature (MVVM)

#### [NEW] [TuKaiUiState.java](file:///D:/Documents/Desarrollo de Software/Tercer año/Moviles/AndroidStudioProjects/KAI/app/src/main/java/com/roma/kai/ui/kai/TuKaiUiState.java)
- Holds the UI state for the Tu Kai screen.

#### [NEW] [TuKaiViewModel.java](file:///D:/Documents/Desarrollo de Software/Tercer año/Moviles/AndroidStudioProjects/KAI/app/src/main/java/com/roma/kai/ui/kai/TuKaiViewModel.java)
- Manages the logic for loading and processing Kai's data.

#### [TuKaiFragment.java](file:///D:/Documents/Desarrollo de Software/Tercer año/Moviles/AndroidStudioProjects/KAI/app/src/main/java/com/roma/kai/ui/kai/TuKaiFragment.java)
- Update to observe `TuKaiViewModel` and update the UI.

#### [fragment_tu_kai.xml](file:///D:/Documents/Desarrollo de Software/Tercer año/Moviles/AndroidStudioProjects/KAI/app/src/main/res/layout/fragment_tu_kai.xml)
- Include `RadarChartView` and update the energy section.

## Verification Plan

### Manual Verification
- Deploy the app and navigate to "Tu Kai".
- Verify that the Radar Chart displays correctly with the data.
- Verify that the energy meter (donut and bar) updates according to the state.
- Verify that the stage, personality, and emotional messages are correctly displayed.
