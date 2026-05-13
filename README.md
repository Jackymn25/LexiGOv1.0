[![Discord](https://badgen.net/badge/Discord/Join/5865F2?icon=discord)](https://github.com/Jackymn25/LexiGOv1.0/discussions)

# LexiGO v1.3
[View Accessibility Report](./accessibility_report.md)

*A cross-platform Java desktop app that combines adaptive flash-card drills, rich word references, and gentle gamification to help English speakers build lasting foreign-language vocabulary.*

LexiGO uses a spaced-repetition scheduler to surface each word right when you’re ready to review. Around that core are:

* One-tap daily check-ins
* Example sentences and translations on every card
* Streaks, badges, and leaderboards (optional, friendly competition)

---

## Table of Contents

* [Quick Facts](#quick-facts)
* [Team & Use-Case Owners](#team--use-case-owners)
* [User-Story Matrix](#user-story-matrix)
* [Accessibility Report](#accessibility-report)
* [Getting Started](#getting-started)
* [Usage](#usage)
* [Feedback](#feedback)
* [Contribution Guide](#contribution-guide)
* [License](#license)

---

## Quick Facts

| Item             | Details                                                                           |
| ---------------- | --------------------------------------------------------------------------------- |
| **Domain**       | Vocabulary acquisition for native English speakers                                |
| **Users**        | Age 10+; from casual hobbyists to exam-focused polyglots                          |
| **Tech stack**   | Java 17 · Maven 3.9+ · Swing · JSON persistence                                   |
| **Architecture** | Clean Architecture (entities & use cases & adapters & Swing views)                |
| **Modules**      | Auth & Profile · Check-In/Study · Word Reference · Stats Dashboard · Gamification |
| **OS**           | Windows · macOS · Linux (desktop Java)                                            |

**Codebase structure at a glance**

```text
app/               start-up bootstrap & DI wiring
data_access/       JSON- and in-memory gateways
entity/            core domain models (User, Word, Achievement…)
use_case/          interactors & boundaries per user story
interface_adapter/ controllers, presenters, view-models
view/              Swing panels, navigation, theming
```

---

## Team & Use-Case Owners

| Member         | GitHub        | Primary Use Cases                  |
| -------------- | ------------- | ---------------------------------- |
| Jacky Huo      | `@Jackymn25`  | Start check-in • Study session     |
| Jacob Ke       | `@Y0m1ya`     | View history • Change password     |
| Jincheng Liang | `@Godoftitan` | Leaderboard • Word detail          |
| Heyuan Zhou    | `@HeyuanZ621` | Achievement system                 |
| Yunzhao Li     | `@yunzhaol`   | Profile settings • Finish check-in |

*Team story:* we collectively refined sign-up rules and polished the registration/login flow.

---

## User-Story Matrix

| # | Persona                   | I want to…                                 | So that…                       | Owner    | Team Story |
| - | ------------------------- | ------------------------------------------ | ------------------------------ | -------- | ---------- |
| 1 | Language learner          | Start a check-in and study with flip cards | Keep my daily learning streak  | Jacky    |            |
| 2 | Security-conscious        | Change my password (with verification)     | Protect my account             | Jacob    |            |
| 3 | Reflective learner        | View my historical stats                   | Track progress over time       | Jacob    |            |
| 4 | Competitive learner       | See the leaderboard rankings               | Compete with friends           | Jincheng |            |
| 5 | Inquisitive learner       | Inspect word details during study          | Deepen understanding           | Jincheng |            |
| 6 | Motivated learner         | Earn achievements for milestones           | Feel rewarded and engaged      | Heyuan   |            |
| 7 | Multilingual user         | Manage profile (username, languages)       | Switch courses easily          | Yunzhao  |            |
| 8 | Learner finishing session | Complete a session & finalize check-in     | Log progress and update streak | Yunzhao  |            |
| 9 | **First-time user**       | Sign up with Security Q\&A (optional)      | Start learning immediately     | **Team** | ✔          |

---

## Accessibility Report

**Audience.** Introductory-level English learners using a desktop vocabulary app.
**Model.** We follow the **social model of disability**: barriers live in the product environment, so we design the default UI for everyone—no separate “accessible mode”.

### Universal Design principles → current status

| Principle                           | Status        | What’s in LexiGO today                                                                                                                                       | Next steps (high-impact)                                                                                                       |
| ----------------------------------- | ------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------ |
| **1. Equitable Use**                | Partial       | One UI for all users; no segregated interface.                                                                                                               | Programmatic **name/role/state** for every control; avoid color-only cues; text alternatives for decorative images.            |
| **2. Flexibility in Use** *(focus)* | **Strong**    | **200% text zoom** without content loss; **Dark/Light themes**; mouse and most screens support keyboard.                                                     | Guarantee a **keyboard-only path** for all core tasks; shortcuts (Space=flip, ←/→=prev/next, Enter=submit).                    |
| **3. Simple & Intuitive** *(focus)* | **Strong**    | Plain labels (“Daily Check-in”, “Profile”); predictable flows.                                                                                               | 60-second first-run tips; clearer, actionable error messages.                                                                  |
| **4. Perceptible Information**      | Partial       | High-contrast themes; icons **and** text labels.                                                                                                             | Verify contrast ≥ **4.5:1** on both themes; label inputs via `setLabelFor`; accessible descriptions for logos/illustrations.   |
| **5. Tolerance for Error**          | **Improving** | **Logout confirmation dialog** prevents accidental sign-out (`JOptionPane.YES_NO_OPTION`; **Esc/No** cancels). Duplicate-password check; informative alerts. | Confirm/undo for other destructive actions; link form errors to fields; **Esc** closes dialogs and returns focus consistently. |
| **6. Low Physical Effort**          | **Strong**    | Few steps to start studying; no drag/long-press; generous click targets.                                                                                     | Optional “auto-start study” to cut routine clicks; keyboard shortcuts to reduce hand travel.                                   |
| **7. Size & Space for Use**         | Partial       | Resizable window; large-text mode keeps layout usable.                                                                                                       | Add a min-window size and scrolling in tiny layouts; wrap long labels; enlarge small toggles for touch devices.                |

**Per-principle notes**

* **Equitable Use:** One UI for all; themes and large text benefit everyone. • Future: complete AccessibleContext naming and alt text.
* **Flexibility in Use:** 200% zoom + keyboard/mouse on core screens. • Future: full keyboard paths + shortcuts.
* **Simple & Intuitive:** Clear labels and flows. • Future: brief first-run tips + actionable errors.
* **Perceptible Information:** High contrast, text + icon signals. • Future: verify 4.5:1 contrast; add input labelling and logo descriptions.
* **Tolerance for Error:** **Logout requires confirmation; Esc/No aborts**, plus duplicate-password check. • Future: confirm/undo elsewhere; field-linked errors; consistent Esc handling.
* **Low Physical Effort:** Minimal steps; no precision gestures. • Future: auto-start and shortcuts.
* **Size & Space:** Resizable; large text preserves layout. • Future: min size, scroll when tiny, bigger toggles for touch.

**Market / Audience.**
LexiGO targets **native English speakers aged 10+** who want a fast, low-friction way to build foreign-language vocabulary—students preparing for exams, adult self-learners, travelers, and language-club members. It suits users who prefer a **distraction-free desktop** experience with short daily sessions and gentle motivation (streaks and badges). Schools and clubs can deploy LexiGO in labs because it runs as a **single JAR** with local storage. Privacy-minded learners who dislike cloud lock-in appreciate **offline-friendly** study and on-device data.

**Access & ethics.**
Today the app may be **less usable for screen-reader and keyboard-only users** because some controls lack programmatic names/roles and a complete keyboard path. Learners whose **UI language isn’t supported** face an environmental barrier; we are expanding localization. We are closing gaps by adding accessible names, visible focus, dialog **Esc** handling, and field-linked error messages. Desktop-only distribution may also exclude people who rely solely on mobile; a lightweight web/desktop hybrid is on our roadmap.

---

## Getting Started

```bash
# 1) Clone the repo
git clone https://github.com/Jackymn25/LexiGOv1.0
cd LexiGOv1.0

# 2) Build (style + dependency checks)
mvn -U clean verify   # requires Maven 3.9+ and JDK 17

# 3) Launch the app
mvn exec:java -Dexec.mainClass="app.Main"
```

### Requirements

* **Java 17** (Adoptium or equivalent)
* **Maven 3.9+**
* OS: **Windows / macOS / Linux**

### Troubleshooting

* `mvn: command not found` → install Maven and add to PATH.
* `Unsupported class file major version` → using older JDK; switch to **JDK 17**.
* App doesn’t start → run `java -version` / `mvn -v`; then `mvn -U clean verify`.

---

## Usage

1. **Sign up or log in.** (Security Q\&A is optional.)
2. **Daily Check-in → Study.** Flip cards, view examples/translation, answer, **Next**.
3. **Finish Check-in.** Progress and streak update; open **Stats** for history.
4. **Rank.** Press **Refresh** to see your position on the leaderboard.
   Tips: **A+** toggles 200% text; **Day/Night** switches themes; keyboard shortcuts planned (Space=flip, ←/→=prev/next, Enter=submit).

---

## Feedback

* Open a **GitHub Issue**: [https://github.com/Jackymn25/LexiGOv1.0/issues/new/choose](https://github.com/Jackymn25/LexiGOv1.0/issues/new/choose)
* Or start a **Discussion**: [https://github.com/Jackymn25/LexiGOv1.0/discussions](https://github.com/Jackymn25/LexiGOv1.0/discussions)
* Please include steps to reproduce, expected vs. actual results, and screenshots/logs when possible.

---

## Contribution Guide

* Feature branches → **small, focused PRs**
* Reference a GitHub issue in every PR
* Use Conventional Commits: `feat(scope): summary`
* Run `mvn verify` locally before pushing
* Fork → create branch → open PR → we review within **72 hours**

---

## License

Licensed under the **MIT License**. See [LICENSE](LICENSE) for details

---

*Updated 7 Aug 2025 by the LexiGO Team*
