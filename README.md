# Migration Guide: CareerFlow (Legacy) to JobApp (Modern)

This document outlines the architectural migration from **CareerFlow**, a legacy Spring MVC monolithic application, to **JobApp**, a modern, decoupled system utilizing a Spring Boot REST API and an Angular SPA.

This guide serves as a reference for understanding the structural changes, component mapping, and the engineering decisions behind the modernization.

## 🎯 The "Why": Drivers for Migration

The original CareerFlow application was built as a tightly-coupled monolith where the server handled both business logic and HTML rendering via JavaServer Pages (JSP). We migrated to a decoupled architecture to achieve:
1. **Independent Scalability:** The backend API and frontend UI can now be scaled independently based on traffic demands.
2. **Separation of Concerns:** Backend engineers can focus purely on data delivery and business logic, while frontend engineers can focus on user experience.
3. **Modern User Experience:** Moving from synchronous full-page reloads to a reactive Angular Single Page Application (SPA) drastically improves perceived performance and interactivity.
4. **Future-Proofing:** Deprecating JSP in favor of a standard RESTful JSON contract allows future integrations (e.g., a mobile app) without altering the backend.

## 🏗️ Architecture Comparison

| Feature | Legacy Monolith (`/CareerFlow`) | Modern Decoupled (`/JobApp`) |
| :--- | :--- | :--- |
| **Architecture** | Model-View-Controller (MVC) | Client-Server (REST API + SPA) |
| **View Layer** | JSP (Server-side rendering) | Angular (Client-side rendering) |
| **Data Format** | HTML pages injected with Model attributes | JSON (JavaScript Object Notation) |
| **Routing** | Handled by Spring `DispatcherServlet` | Handled entirely client-side by Angular Router |
| **State Management**| Server-side HTTP Sessions | Client-side application state |

## 🗺️ Component Mapping Guide

If you are transitioning from the legacy codebase to the new one, use this mapping to locate equivalent functionality:

### 1. The Controller Layer
In the legacy app, controllers returned View names. In the modern app, they return raw data (JSON).
* **Legacy:** `CareerFlow/.../Controller/JobsController.java` (`@Controller`)
* **Modern:** `JobAppRest/.../Controller/JobsRestControler.java` (`@RestController`)

### 2. The View Layer (UI)
JSP files have been completely replaced by modular Angular components built with TypeScript and SCSS.
* **Legacy:** `src/main/webapp/views/homePage.jsp`
  **Modern:** `JobAppUi/src/app/MyComponent/home/`
* **Legacy:** `src/main/webapp/views/addJobs.jsp`
  **Modern:** `JobAppUi/src/app/MyComponent/add-job/`
* **Legacy:** `src/main/webapp/views/viewJobs.jsp`
  **Modern:** `JobAppUi/src/app/MyComponent/view-jobs/` & `job-item/`
* **Legacy:** `src/main/webapp/views/header.jsp`
  **Modern:** `JobAppUi/src/app/MyComponent/header/`

### 3. The Data Layer
The core domain model (`Jobs.java`) and the persistence layer logic largely remained the same, demonstrating the strength of keeping business logic isolated from the presentation layer. The modern backend now utilizes `JobJpaDao.java` for streamlined database operations.

## 🔄 The Migration Process (Step-by-Step)

1. **Backend API Extraction:**
   * Created a new Spring Boot project (`JobAppRest`).
   * Migrated the `Jobs` entity and related Data Access Objects (DAOs).
   * Rewrote the legacy `@Controller` classes into `@RestController` classes.
   * Replaced `ModelAndView` and `Model` object populations with direct entity/DTO returns via `ResponseEntity`.
2. **Frontend Initialization:**
   * Bootstrapped a new Angular application (`JobAppUi`).
   * Configured the Angular Router to mirror the legacy navigation paths.
3. **UI Componentization:**
   * Broke down the legacy JSP pages into reusable Angular components (Header, Job Item, View Jobs, Add Job).
   * Translated legacy CSS into scoped SCSS for individual components.
4. **Service Integration:**
   * Implemented Angular Services (`job-service.ts`) using `HttpClient` to asynchronously fetch and post data to the new Spring Boot REST API.

## 🧪 Running the Environments

To see the evolution firsthand, you can run both environments locally.

**To run the Legacy Monolith:**
Navigate to `/CareerFlow` and run `mvn spring-boot:run`. The application with JSP rendering will be available at `http://localhost:8080`.

**To run the Modern Architecture:**
1. Start the API: Navigate to `/JobApp/JobAppRest` and run `mvn spring-boot:run` (Runs on port 8080).
2. Start the UI: Navigate to `/JobApp/JobAppUi`, run `npm install`, then `ng serve` (Runs on port 4200).
