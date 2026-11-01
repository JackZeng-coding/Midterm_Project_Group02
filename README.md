# INFO 5100 Midterm Project: The Digital University Model with Access Control

## Team Information
**Group 02 Name: "The Role Players"**
| Name | NUID | Use Case | Also responsible for |
|---|---|---|---|
| Jiahao Zeng (Jack) | 003155514 | Admin | Login/logout and role routing, connecting the access-control layer to the university model, demo data, Faculty extras |
| Gavin Wen | 003946352 | Student | README skeleton, shared profile panel, registration and coursework model, coursework demo data |
## Project Overview
### Purpose
This project integrates a reference implementation of a Digital University System with an access-control layer. Users log in, and each role can reach only the features assigned to it.
### Scope
Because our team has two members (one member dropped the course), Prof. Bugrara confirmed (via the TA) that we should each implement one use case of our choosing, for a total of two use cases. Part of the Faculty use case (Manage Courses and My Profile) was implemented before this confirmation and is kept as an extra; the two remaining Faculty buttons are disabled.
### Key Features
- Role-based login with default deny: every user is routed to the work area of their own role.
- Admin: register persons, administer user accounts, manage student and faculty records, manage own profile.
- Student: register for and drop classes, manage coursework, perform a graduation audit, review the transcript, manage own profile.
- Input validation (names, emails, usernames, passwords) and null checks.
- Safe deletion: a person with course history cannot be deleted.
- Faculty extras: manage own courses and own profile.

## Installation & Setup
### Prerequisites
- JDK 17 or newer (the project's source/binary format is 17)
- Apache NetBeans (tested with NetBeans 31 + JDK 26 on macOS and NetBeans 16 + JDK 19 on Windows)
- No external libraries

### Steps
1. Clone the repository: `git clone https://github.com/JackZeng-coding/Midterm_Project_Group02.git` (or use GitHub Desktop；or download .zip file summitted to Canvas).
2. In NetBeans: File → Open Project → select the cloned folder.
3. Run → Clean and Build Project.
4. Run the project. The main class is `Business.ProfileWorkAreaMainFrame`.

> All data lives in memory. The demo data is loaded again on every start, and new persons get IDs from 1007.

## Authentication & Access Control
| Rule | Where it is enforced |
|---|---|
| Default deny: only known profile types get a work area | `ProfileWorkAreaMainFrame` (login routing) |
| Usernames are unique (case-insensitive) | `UserAccountDirectory.isUsernameTaken()`, `RegisterPersonsJPanel` |
| Passwords need at least 6 characters | `RegisterPersonsJPanel`, `ManageAccountsJPanel` |
| Only Admins can register persons and manage accounts and records; registering as "Admin (Staff)" grants admin rights | `AdminRoleWorkAreaJPanel` (reachable only with an Employee profile) |
| An Admin cannot delete the account they are logged in with | `ManageAccountsJPanel` |
| Deleting an account removes only the login; the person record is kept | `ManageAccountsJPanel`, `UserAccountDirectory.removeUserAccount()` |
| A student or faculty record with any course history cannot be deleted; otherwise the login, both profiles and the person are removed | `ManageRecordsJPanel`, `StudentDirectory.removeStudent()`, `FacultyDirectory.removeFaculty()` |
| Faculty (extra): see only their own courses, edit only current-semester courses, course name and credits are read-only | `FacultyManageCoursesJPanel` |

## Features Implemented
### Student — Gavin Wen
<!-- Gavin: TODO -->


### Admin — Jiahao Zeng
| Responsibility | What it does | PR |
|---|---|---|
| Register Persons | Creates a person, a Student / Faculty / Admin (Staff) profile and a login account in one step, with validation | #13 |
| Administer User Accounts | Lists all accounts; changes a password; deletes an account (never your own) | #14 |
| Manage Student Records | List, view, edit name and email, delete under the course-history rule | #15 |
| Manage Faculty Records | The same panel, opened in faculty mode | #15 |
| Manage Own Profile | View and edit your own name and email (shared profile panel) | #16 |

### Faculty (extra, built before the scope change) — Jiahao Zeng
| Feature | What it does | PR |
|---|---|---|
| Manage Courses | Lists the teacher's own courses; edits meeting time and description for the current semester | #12 |
| My Profile | The same shared profile panel | #16 |
| Performance Reports / Manage Student Profiles | Disabled: outside the scope of a 2-person team | #18 |

### Shared / Integration — Jiahao Zeng
| Work | PR |
|---|---|
| Login / logout and role-based routing | #6 |
| Demo data: people, accounts, courses, grades, teaching assignments | #5 |
| Faculty model linking both layers | #8 |
| Teaching assignments; course offer semester, capacity, enrolled count | #11 |
| Connecting the access-control layer to the university model; model bug fixes | #3 |

## Usage Instructions
### Demo Accounts
| Username | Password | Role | Person (ID) |
|---|---|---|---|
| admin | **** | Admin | John Smith (1000) |
| gina | **** | Faculty | Gina Montana (1001) |
| adam | **** | Student | Adam Rollen (1002) |
| laura | **** | Faculty | Laura Brown (1005) |
### Example Scenarios
**Scenario 1: A student's life cycle (Admin + Student)**
1. Log in as `admin` → Register Persons → register a new Student with a username and password → Logout.
2. Log in as the new student → Register for Classes → enroll in INFO 6205.
3. Coursework → select INFO 6205 → choose an assignment → type your work → Submit → Logout.
4. Log in as `admin` → Manage Students → delete the new student → blocked, because the student has a course record.
5. Log in as the new student → Register for Classes → drop INFO 6205 → Logout.
6. Log in as `admin` → Manage Students → delete the new student → deleted; that username can no longer log in.

**Scenario 2: Authentication and access control**
1. Log in as `adam` with a wrong password → "Invalid username or password."
2. Log in as `adam` with the right password → only the Student work area appears.
3. Logout → log in as `admin` → only the Admin work area appears.
4. Administer User Accounts → try to delete the `admin` account → rejected.

## Testing Guide
Authentication is covered by A-01 to A-03 and A-11; authorization by A-04, A-07, A-08 and the Student tests.
| ID | Steps | Expected Result |
|---|---|---|
| A-01 | Log in with a wrong password | "Invalid username or password."; the password field is cleared |
| A-02 | Leave the username empty and click Login | "Please enter both username and password." |
| A-03 | Log in as `ADMIN` (upper case) | Login succeeds (usernames are case-insensitive) |
| A-04 | Log in as admin, adam and gina in turn | Each user sees only their own work area |
| A-05 | Register a person with the username `Adam` | Rejected: the username is taken |
| A-06 | Register with the name `Ann3` or the email `a@b` | Rejected with a format warning |
| A-07 | Administer User Accounts → delete the admin account while logged in as admin | Rejected |
| A-08 | Manage Students → delete Adam | Blocked: "has 3 course record(s)" |
| A-09 | Register a new student → Manage Students → delete them | Deleted; that username can no longer log in |
| A-10 | Manage Students → change Adam's first name → open Administer User Accounts | The new name appears in both places |
| A-11 | Click Logout | The work area is cleared and the login form unlocks |
 
### Student tests (Gavin Wen)
<!-- Gavin TODO: please add testing Guide for Student Part -->
| ID | Steps | Expected Result |
|---|---|---|


## Challenges & Solutions
1. **Challenges：Collaborating across time zones (EST / JST).** **Solution:** we kept in touch on Teams, used one shared three-hour window for joint work, and left notes in Teams or in the pull request whenever we changed something.
2. **Challenges：NetBeans differences between macOS and Windows.** Fixed component sizes in Null Layout cut off text on macOS. **Solution:** we checked each other's screens on both systems and widened the components where needed.
3. **Challenges：Two layers with classes of the same names** (`Person`, `StudentProfile`, `FacultyProfile` and others exist in both the access-control layer and the university model). **Solution:** the two layers share only the person ID, and university-model classes are written with their full package name.

## Future Enhancements
1. Implement the two remaining Faculty features (Performance Reports and Manage Student Profiles).
2. Save data to a database or files; at the moment all data is lost when the program closes.
3. Keep a log of Admin actions.
4. Add search and filters to the tables.

## Contribution Breakdown
### Together
- Split the use cases, planned the work and shared daily updates on Teams.
- Final cross-test on macOS and Windows, and recorded the demo video together.

### Gavin Wen
<!-- Gavin TODO: please review and add details -->
- **Coding:** Student use case: review transcript (#7), graduation audit (#10), register for classes (#17), manage coursework (#19); the shared profile panel (#9), later reused by Admin and Faculty.
- **Documentation:** README skeleton (#1) and Student sections; slides for the Student use case and challenges.
- **Testing:** test steps in each pull request; reviewed and approved Jack's pull requests.
- **Pull requests:** 6 (#1, #7, #9, #10, #17, #19) · **Commits:** 33

### Jiahao Zeng
- **Coding:** Admin use case (#13–#16); login/logout and default-deny routing (#6); connecting the two layers and model fixes (#2–#4); demo data (#5); faculty model and teaching assignments (#8, #11); Faculty extras (#12, #16, #18).
- **Documentation:** README structure, Admin and shared sections; slides for the overview, architecture, access control and Admin use case.
- **Testing:** test steps in each pull request; reviewed and tested Gavin's pull requests before merging.
- **Pull requests:** 13 (#2–#6, #8, #11–#16, #18) · **Commits:** 51

## Team Rules
- One branch per feature (e.g., 'name/feature').
- All branches created from the last 'main'.
- Always include '@author' in newly created or modified files.

## AI Usage
### Jiahao Zeng
I used Claude (Anthropic) to explain Java and NetBeans concepts, debug errors, suggest code for Manage Students features, Faculty panel and the delete Methods. The design decisions and the testing were done by our team.

## Links
- Demo video (YouTube, unlisted): 
- Presentation slides (Canva): https://canva.link/s3omvghx1ybhgfn













