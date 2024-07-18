# Tool Rental Application Design Document

## Table of Contents

1. [Introduction](#introduction)
2. [System Overview](#system-overview)
3. [Requirements](#requirements)
   - [Functional Requirements](#functional-requirements)
   - [Non-Functional Requirements](#non-functional-requirements)
4. [Architecture](#architecture)
   - [High-Level Architecture](#high-level-architecture)
   - [Modules](#modules)
5. [Data Model](#data-model)
6. [User Interface](#user-interface)
7. [Security](#security)
8. [Deployment](#deployment)

## Introduction

The Tool Rental Application is designed to facilitate the rental of tools from a rental shop. This document outlines the architecture and design of the application, providing a blueprint for its development.

## System Overview

The Tool Rental Application will be Java based and will allow clerks to generate a rental agreement.

## Requirements

### Functional Requirements

1. Tool Checkout (see Spec)

### Non-Functional Requirements

1. Java version: Use Java 11 or greater. Prefer Java 21.
2. Rental Agreement formatting: (see Spec)
3. Internationalization: Ignore for now.
4. Unit tests: Full coverage.

## Architecture

### High-Level Architecture

The application will follow a simple layered architecture consisting of:

1. Business Logic Layer: Basic functionality (i.e. tool checkout).
2. Data Access Layer: Tool catalog.

### Modules

1. **Tool Management Module**: Manages the tool catalog.
2. **Rental Management Module**: Manages tool checkout process.

## Data Model

### Tool (see Spec)

- `code`: String
- `toolType`: ToolType
- `brand`: Brand

### ToolType (see Spec)

- `name`: String
- `rentalPrice`: Double
- `weekdayCharge`: Boolean
- `weekendCharge`: Boolean
- `holidayCharge`: Boolean

### Brand

- `name`: String

### RentalAgreement (see Spec)

## Persistence Layer

### ToolCatalog

Repository of avaiable tools. Static list for now. Use an Interface to allow for future implementations.

## Business Layer

### ToolRental

Most of the business logic for performing a tool rental. Performs validation on rental requests.

## User Interface

- Not applicable.

## Security

- Not applicable.

## Deployment

- Standalone app with cli.
