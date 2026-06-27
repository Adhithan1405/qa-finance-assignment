# Manual Test Cases — OrangeHRM Login Module

**Application:** OrangeHRM Demo  
**URL:** https://opensource-demo.orangehrmlive.com  
**Module Under Test:** Login  

---

## Test Cases

| TC ID | Test Description | Steps | Expected Result | Priority |
|-------|-----------------|-------|-----------------|----------|
| TC-001 | Valid login with correct credentials | 1. Navigate to login page <br> 2. Enter Username: `Admin` <br> 3. Enter Password: `admin123` <br> 4. Click **Login** | User is redirected to the Dashboard. Title "Dashboard" is visible. | High |
| TC-002 | Login and verify dashboard header shows username | 1. Login with `Admin` / `admin123` <br> 2. Observe top-right header area | User name or avatar is visible in the header confirming active session. | High |
| TC-003 | Login with incorrect password | 1. Navigate to login page <br> 2. Enter Username: `Admin` <br> 3. Enter Password: `wrongpassword` <br> 4. Click **Login** | Error message "Invalid credentials" is displayed. User stays on login page. | High |
| TC-004 | Login with non-existent username | 1. Navigate to login page <br> 2. Enter Username: `unknownuser` <br> 3. Enter Password: `admin123` <br> 4. Click **Login** | Error message "Invalid credentials" is displayed. Login is denied. | High |
| TC-005 | Login with empty username | 1. Navigate to login page <br> 2. Leave Username blank <br> 3. Enter Password: `admin123` <br> 4. Click **Login** | Inline validation "Required" appears under Username. Form is not submitted. | High |
| TC-006 | Login with empty password | 1. Navigate to login page <br> 2. Enter Username: `Admin` <br> 3. Leave Password blank <br> 4. Click **Login** | Inline validation "Required" appears under Password. Form is not submitted. | High |
| TC-007 | Login with both fields empty | 1. Navigate to login page <br> 2. Leave both fields blank <br> 3. Click **Login** | "Required" validation shown under both fields. Form is not submitted. | Medium |
| TC-008 | Boundary — single character password | 1. Navigate to login page <br> 2. Enter Username: `Admin` <br> 3. Enter Password: `a` <br> 4. Click **Login** | "Invalid credentials" error displayed. App handles minimal input without crashing. | Medium |

---

## Coverage Summary

| Category | TC IDs |
|----------|--------|
| Positive scenarios | TC-001, TC-002 |
| Negative scenarios | TC-003, TC-004, TC-005, TC-006, TC-007 |
| Boundary value | TC-008 |
