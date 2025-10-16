# Proyecto CultivApp - Monorepo

This repository contains the full CultivApp project: a React frontend and a Spring Boot backend.

## What does this folder do?
Root folder orchestrates both applications, shared docs, and startup scripts.

## How to install this part
- No installation here. Install each app in its own folder (see `client/` and `server/`).

## How to run this part
- Use the helper scripts:
  - Windows PowerShell: `./start-all.ps1`
  - Batch: `start-frontend.bat` and `start-backend.bat`

## Standards to consider
- Conventional Commits for messages
- EditorConfig/Prettier (frontend) and Checkstyle-like conventions (backend)

## Language/runtime versions
- Frontend: Node.js 20+, React 18
- Backend: Java 21, Spring Boot 3.5.x

## Database requirements
- H2 file-based db for development (see `server/src/cultivapp/cultivapp/src/main/resources/application.properties`).
# CultivApp

**Decisiones de cultivo basadas en clima real + fenología del cultivo, sin hardware, asequible y útil.**

**CultivApp** es una plataforma web que ayuda a pequeños y medianos productores a gestionar sus cultivos con recordatorios y alertas **sin sensores costosos**. Cruza datos del cultivo con el **clima real** (consultado desde SIATA vía web scraping o a través de un microservicio API propio) para sugerir acciones: riego, fertilización y alertas climáticas.

## Integrantes
- Nicolás Meneses — nicolas.meneses@upb.edu.co
- Diego Villegas — diego.villegasv@upb.edu.co
- Simón Bedoya  — simon.bedoyau@upb.edu.co
- Sebastián Quiceno  — sebastian.quicenol@upb.edu.co

## Quick Start

### Prerequisites
- **Java 21** or higher (for backend)
- **Node.js 18+** and npm (for frontend)
- No database installation required (H2 embedded)

### 🚀 Easy Start (Windows)

**Option 1: Start everything at once**
```cmd
start-all.bat
```
This will open two new windows: one for backend, one for frontend.

**Option 2: Start separately**
```cmd
start-backend.bat    # Backend only
start-frontend.bat   # Frontend only
```

### Manual Start

**Backend:**
```powershell
cd server\src\cultivapp\cultivapp
.\mvnw.cmd spring-boot:run
```
Backend will start on **http://localhost:8080**

**Frontend:**
```powershell
cd client\src
npm install  # First time only
npm run dev
```
Frontend will start on **http://localhost:5173** (or 5174 if 5173 is busy)

### Access the Application

- **Frontend UI**: http://localhost:5173 (or check terminal for actual port)
- **Backend API**: http://localhost:8080
- **H2 Console**: http://localhost:8080/h2-console

### Login

Use these sample users:
- **Producer**: `productor@cultivapp.com` / `password`
- **Admin**: `admin@cultivapp.com` / `password`

## Database

CultivApp uses **H2 database in file-based mode** for local development:

- **Location**: `server/.data/cultivapp-dev.mv.db`
- **Mode**: PostgreSQL-compatible
- **Persistence**: Data survives restarts
- **No installation**: Embedded database
- **Reset**: Delete the file and restart

See [Database Setup Guide](doc/db-setup.md) for details.

## Project Structure

```
├── client/              # React + Vite frontend
│   ├── src/
│   │   ├── Components/  # Reusable UI components
│   │   ├── pages/       # Page components (Login, etc.)
│   │   ├── services/    # API communication layer
│   │   └── context/     # React context (auth state)
│   └── package.json
│
├── server/              # Spring Boot backend
│   └── src/cultivapp/cultivapp/
│       ├── src/main/java/com/cultivapp/cultivapp/
│       │   ├── auth/        # Authentication (login, JWT)
│       │   ├── model/       # JPA entities (database models)
│       │   └── repository/  # Data access layer
│       └── src/main/resources/
│           ├── application.yml      # Base config
│           ├── application-dev.yml  # Development config
│           ├── schema.sql           # Database schema
│           └── data.sql             # Sample data
│
└── doc/                 # Documentation
    ├── db-setup.md      # Database setup guide
    ├── analysis/        # Requirements and analysis
    └── design/          # Design documents
```

## Implemented Features

### ✅ REQ-001: Login Authentication
- JWT-based authentication
- Role-based access (ADMIN, PRODUCTOR)
- Secure password hashing (BCrypt)
- Account status management

### 🚧 In Progress
- CRUD for Cultivos (crops)
- Admin catalog for Especies (plant species)
- Task management
- Alert system

## Technology Stack

### Backend
- Java 21
- Spring Boot 3.5.6
- Spring Security (JWT)
- Spring Data JPA (Hibernate)
- H2 Database (file-based)
- Maven

### Frontend
- React 18
- Vite
- React Router
- Context API (state management)
- Axios (HTTP client)

## Estructura y navegación

- [Documentación](doc/index.md) — Portal de documentación  
- [Database Setup](doc/db-setup.md) — Database configuration guide
- [Análisis](doc/analysis/index.md) — Requisitos y artefactos de la fase de análisis  
  - [Requisitos Funcionales](doc/analysis/requirements-fn.md) — 20 requisitos en notación Gherkin  
  - [Requisitos No Funcionales](doc/analysis/requirements-nfn.md) — 10 requisitos priorizados   
