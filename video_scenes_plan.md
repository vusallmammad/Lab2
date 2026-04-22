# 🎥 Video Scene Shooting Plan
## Farm Inventory Manager — Capstone Showcase
### 26 Scenes · Total ≈ 20 minutes

---

> **Legend**
> - 📷 **CAMERA** — person visible on webcam / phone camera, talking to lens
> - 🖥️ **SCREEN** — screen recording only (app or code editor open)
> - ✂️ **SPLIT** — screen recording + small webcam overlay in corner

---

## ── SECTION 1: Project Overview (≈ 2 min) ──────────────────────

### 🎬 Scene 1 — Title Card + Problem Statement
| Item | Detail |
|------|--------|
| **Type** | 📷 CAMERA |
| **Duration** | ~45 sec |
| **Who** | Any team member |
| **What's on screen** | Title slide: *"Farm Inventory Manager — Aqrar Ehtiyat İdarəetmə Sistemi"* behind speaker, or speaker in front of camera with slide on a second monitor |
| **What to say** | Explain the 3-part problem: (1) paper-based tracking leads to missed shortages, (2) no unified income/expense/stock view, (3) language barriers block digital adoption |

---

### 🎬 Scene 2 — Project Objectives Slide
| Item | Detail |
|------|--------|
| **Type** | 📷 CAMERA |
| **Duration** | ~35 sec |
| **Who** | Same member |
| **What's on screen** | Objectives slide (5 bullets) OR speaker on camera reading list |
| **What to say** | List: real-time inventory, stock alerts, multi-language, voice input, financial tracking |

---

### 🎬 Scene 3 — Login & Dashboard First Look
| Item | Detail |
|------|--------|
| **Type** | 🖥️ SCREEN |
| **Duration** | ~30 sec |
| **Who** | Screen only |
| **What to do** | Open browser → go to `/login/` → type credentials → press Login → land on Dashboard → slowly pan over the dashboard cards so the assessor can read them |
| **What to say** | Brief voiceover: *"This is the live running application — let's start from the login screen."* |

---

## ── SECTION 2: Key Technical Challenges (≈ 3–4 min) ──────────────

### 🎬 Scene 4 — 14-App Architecture Overview
| Item | Detail |
|------|--------|
| **Type** | ✂️ SPLIT |
| **Duration** | ~45 sec |
| **Who** | Member responsible for architecture |
| **What's on screen** | File explorer open at `backend/apps/` showing all 14 folders + architecture text diagram (paste into a slide or Markdown viewer) |
| **What to say** | Explain how 14 apps are loosely coupled; the notification engine reads from all stock apps without those apps depending on it |

---

### 🎬 Scene 5 — Stock Alert Algorithm (Code)
| Item | Detail |
|------|--------|
| **Type** | 🖥️ SCREEN |
| **Duration** | ~50 sec |
| **Who** | Member who built notifications |
| **What's on screen** | VS Code open at `backend/apps/notifications/services.py`, scroll to `build_stock_alerts` function (~line 425). Highlight the `critical_threshold = threshold * Decimal("0.5")` line with your cursor |
| **What to say** | Explain the 3-tier priority logic: system-important → custom rule → disabled. Explain 50% threshold = critical |

---

### 🎬 Scene 6 — Voice Transcription Demo
| Item | Detail |
|------|--------|
| **Type** | 🖥️ SCREEN |
| **Duration** | ~40 sec |
| **Who** | Member who built inventory/voice |
| **What to do** | Navigate to the Add Product / Stok Əlavə Et page → click the microphone button → speak a product name → show the text appearing in the form field |
| **What to say** | Explain the WebM→WAV transcoding challenge, faster-whisper cold-start solution |

---

### 🎬 Scene 7 — Language Switch Demo
| Item | Detail |
|------|--------|
| **Type** | 🖥️ SCREEN |
| **Duration** | ~35 sec |
| **Who** | Member who built i18n |
| **What to do** | Open Settings / Profile page → switch language from Azerbaijani → English (whole UI updates) → then to Russian → back to Azerbaijani |
| **What to say** | Briefly explain `UserLanguageMiddleware` and the `.po` catalogue files |

---

### 🎬 Scene 8 — Unit Conversion Code
| Item | Detail |
|------|--------|
| **Type** | 🖥️ SCREEN |
| **Duration** | ~30 sec |
| **Who** | Member who built dashboard |
| **What's on screen** | VS Code at `dashboard/views.py` lines 43–54 — `_convert_farm_qty` function |
| **What to say** | Explain why comparing 0.5 tonnes to 25 kg threshold fails without normalisation |

---

## ── SECTION 3: Development & Technical Work (≈ 8–9 min) ──────────

### 🎬 Scene 9 — System Architecture Diagram
| Item | Detail |
|------|--------|
| **Type** | 🖥️ SCREEN |
| **Duration** | ~40 sec |
| **Who** | Any member (voiceover) |
| **What's on screen** | Pre-made architecture slide/diagram showing: Browser → WhiteNoise → Django → PostgreSQL/SQLite/Cache/Whisper |
| **What to say** | Walk through each layer: browser renders server-side HTML, WhiteNoise serves static files, PostgreSQL with SSL in production, SQLite auto-used in tests |

---

### 🎬 Scene 10 — Data Model Diagram
| Item | Detail |
|------|--------|
| **Type** | 🖥️ SCREEN |
| **Duration** | ~50 sec |
| **Who** | Any member (voiceover) |
| **What's on screen** | ER diagram slide (made from the diagram in the script) showing User → all 10 related models |
| **What to say** | Explain per-user data isolation via `created_by` FK. Highlight `StockAlertRule.item_key` as a string (not FK) to cover both catalogue and free-text entries |

---

### 🎬 Scene 11 — Dashboard Caching Code
| Item | Detail |
|------|--------|
| **Type** | ✂️ SPLIT |
| **Duration** | ~45 sec |
| **Who** | Member who built dashboard |
| **What's on screen** | VS Code at `dashboard/views.py` lines 761–774 — the versioned cache key block |
| **What to say** | Explain bust_value invalidation on every write, and v6 version tag for global reset during development |

---

### 🎬 Scene 12 — Calendar Page Live Demo
| Item | Detail |
|------|--------|
| **Type** | 🖥️ SCREEN |
| **Duration** | ~60 sec |
| **Who** | Member who built calendar/dashboard |
| **What to do** | Navigate to `/dashboard/calendar/` → click on a day with multiple activities → show the right-side activity panel updating (stock-in, expense, income entries) → click another day → scroll to previous month |
| **What to say** | Explain the merge of 6 models into one timeline and the income deduplication logic |

---

### 🎬 Scene 13 — Calendar Activity Map Code
| Item | Detail |
|------|--------|
| **Type** | 🖥️ SCREEN |
| **Duration** | ~40 sec |
| **Who** | Member who built dashboard |
| **What's on screen** | VS Code at `dashboard/views.py` — `_build_calendar_activity_map` function start (~line 331). Scroll slowly to show seeds → animals → tools → products → incomes → expenses blocks |
| **What to say** | *"Each model is queried, normalised to the same dict structure, and appended to a date-keyed dictionary"* |

---

### 🎬 Scene 14 — Barcode System Demo
| Item | Detail |
|------|--------|
| **Type** | 🖥️ SCREEN |
| **Duration** | ~50 sec |
| **Who** | Member who built inventory |
| **What to do** | Navigate to Inventory → Barcode page → show a generated barcode → use the scan/lookup field → type or scan a code → show it redirecting to the pre-filled form |
| **What to say** | Explain `UserBarcode` model — code, form_type, target_type, cryptographic signature, UniqueConstraint |

---

### 🎬 Scene 15 — Notification Engine Code
| Item | Detail |
|------|--------|
| **Type** | ✂️ SPLIT |
| **Duration** | ~50 sec |
| **Who** | Member who built notifications |
| **What's on screen** | VS Code at `notifications/services.py` lines 472–515 — `sync_stock_alert_notifications`. Highlight `get_or_create` line and `stale_notifications.delete()` |
| **What to say** | Explain the idempotent sync pattern: creates alerts that are new, deletes alerts for items now above threshold |

---

### 🎬 Scene 16 — Supplier Module Demo
| Item | Detail |
|------|--------|
| **Type** | 🖥️ SCREEN |
| **Duration** | ~35 sec |
| **Who** | Member who built suppliers |
| **What to do** | Navigate to Suppliers (Təchizatçılar) page → show the list with categories, ratings, phone numbers → click one supplier to open its detail |
| **What to say** | Mention phone normalisation, display_category property, rating field |

---

### 🎬 Scene 17 — i18n Code Evidence
| Item | Detail |
|------|--------|
| **Type** | 🖥️ SCREEN |
| **Duration** | ~35 sec |
| **Who** | Member who built i18n |
| **What's on screen** | File explorer open at `backend/locale/` → open the `az/LC_MESSAGES/django.po` file in VS Code → show several `msgid` / `msgstr` pairs |
| **What to say** | Explain the automation scripts (`append_translations.py`, etc.) used to keep all 3 locale files in sync |

---

### 🎬 Scene 18 — Offline Sync App
| Item | Detail |
|------|--------|
| **Type** | 🖥️ SCREEN |
| **Duration** | ~20 sec |
| **Who** | Any member |
| **What's on screen** | File explorer at `backend/apps/sync/` → open the URLs file to show push/status endpoints |
| **What to say** | Brief: *"The sync app provides push and status endpoints for queuing operations when connectivity is intermittent"* |

---

## ── SECTION 4: Results & Achievements (≈ 3–4 min) ──────────────

### 🎬 Scene 19 — Full Live Demo Walkthrough
| Item | Detail |
|------|--------|
| **Type** | 🖥️ SCREEN |
| **Duration** | ~90 sec |
| **Who** | Any member (voiceover) |
| **What to do — in order** | |
| Step 1 | Dashboard — point to Weekly Income, Weekly Expense, Net Balance cards |
| Step 2 | Navigate to Seeds → click Add Seed → fill in name, qty, unit, price → Save |
| Step 3 | Return to Dashboard → observe stock count card updated |
| Step 4 | Navigate to Notifications (Bildirişlər) → show stock alert badge count in header |
| Step 5 | Open Stock Alerts page → show Critical (red) vs Low (yellow) items with progress bars |
| Step 6 | Navigate to Suppliers — scroll through list |
| Step 7 | Navigate to Calendar → click an active day → show activity detail panel |
| Step 8 | Switch language: Az → En → Ru → Az |

---

### 🎬 Scene 20 — Stock Alert Severity Evidence
| Item | Detail |
|------|--------|
| **Type** | ✂️ SPLIT |
| **Duration** | ~40 sec |
| **Who** | Member who built notifications (on camera explaining) |
| **What's on screen** | Stock Alerts page open — show items sorted critical first, then low. Disable one alert rule and show item disappearing from list |
| **What to say** | Default thresholds per unit (25 kg seed, 10 units tools, 20 L liquids). Two severity levels: < threshold = low; < 50% threshold = critical |

---

### 🎬 Scene 21 — Test Suite Run
| Item | Detail |
|------|--------|
| **Type** | 🖥️ SCREEN |
| **Duration** | ~30 sec |
| **Who** | Any member |
| **What to do** | Open terminal in `backend/` folder → type `python manage.py test` → let it run → show green OK output |
| **What to say** | *"All tests pass against SQLite — no PostgreSQL connection required to run the test suite"* |

---

### 🎬 Scene 22 — Cache Performance Evidence
| Item | Detail |
|------|--------|
| **Type** | 🖥️ SCREEN |
| **Duration** | ~30 sec |
| **Who** | Any member |
| **What to do** | Open browser DevTools (F12) → Network tab → reload the dashboard once (cold cache, slower) → reload again (warm cache, near-instant) → show the time difference in the Network tab |
| **What to say** | *"The warm cache serves the dashboard with zero database queries"* |

---

## ── SECTION 5: Individual Contributions & Conclusion (≈ 2–3 min) ──

### 🎬 Scene 23 — Member 1 on Camera (Dashboard / Calendar)
| Item | Detail |
|------|--------|
| **Type** | 📷 CAMERA |
| **Duration** | ~35 sec |
| **Who** | Team Member 1 |
| **What to say** | My responsibility → Calendar activity map → income deduplication challenge + solution |

---

### 🎬 Scene 24 — Member 2 on Camera (Notifications / Alerts)
| Item | Detail |
|------|--------|
| **Type** | 📷 CAMERA |
| **Duration** | ~35 sec |
| **Who** | Team Member 2 |
| **What to say** | My responsibility → build_stock_alerts + sync function → idempotent sync pattern challenge + solution |

---

### 🎬 Scene 25 — Member 3 on Camera (Inventory / Voice)
| Item | Detail |
|------|--------|
| **Type** | 📷 CAMERA |
| **Duration** | ~35 sec |
| **Who** | Team Member 3 |
| **What to say** | My responsibility → barcode scan redirect system → WebM→WAV transcoding challenge + av library solution |

---

### 🎬 Scene 26 — Member 4 on Camera (i18n / Suppliers) + Conclusion
| Item | Detail |
|------|--------|
| **Type** | 📷 CAMERA |
| **Duration** | ~45 sec |
| **Who** | Team Member 4 (+ all members briefly if possible) |
| **What to say** | My responsibility → translation automation scripts → Conclusion: 3 future improvements (mobile app, predictive restocking, PDF export) |

---

## 📋 Complete Ordered Scene List (Quick Reference)

| # | Scene | Type | Duration | Section |
|---|-------|------|----------|---------|
| 1 | Title card + problem statement | 📷 Camera | 45s | Intro |
| 2 | Project objectives slide | 📷 Camera | 35s | Intro |
| 3 | Login → Dashboard first look | 🖥️ Screen | 30s | Intro |
| 4 | 14-app architecture diagram | ✂️ Split | 45s | Challenges |
| 5 | Stock alert algorithm code | 🖥️ Screen | 50s | Challenges |
| 6 | Voice transcription demo | 🖥️ Screen | 40s | Challenges |
| 7 | Language switch demo | 🖥️ Screen | 35s | Challenges |
| 8 | Unit conversion code | 🖥️ Screen | 30s | Challenges |
| 9 | System architecture diagram | 🖥️ Screen | 40s | Dev work |
| 10 | Data model ER diagram | 🖥️ Screen | 50s | Dev work |
| 11 | Dashboard caching code | ✂️ Split | 45s | Dev work |
| 12 | Calendar live demo | 🖥️ Screen | 60s | Dev work |
| 13 | Calendar activity map code | 🖥️ Screen | 40s | Dev work |
| 14 | Barcode system demo | 🖥️ Screen | 50s | Dev work |
| 15 | Notification engine code | ✂️ Split | 50s | Dev work |
| 16 | Supplier module demo | 🖥️ Screen | 35s | Dev work |
| 17 | i18n code + `.po` files | 🖥️ Screen | 35s | Dev work |
| 18 | Offline sync app | 🖥️ Screen | 20s | Dev work |
| 19 | Full live demo walkthrough | 🖥️ Screen | 90s | Results |
| 20 | Stock alert severity evidence | ✂️ Split | 40s | Results |
| 21 | Test suite run | 🖥️ Screen | 30s | Results |
| 22 | Cache performance (DevTools) | 🖥️ Screen | 30s | Results |
| 23 | Member 1 on camera | 📷 Camera | 35s | Contributions |
| 24 | Member 2 on camera | 📷 Camera | 35s | Contributions |
| 25 | Member 3 on camera | 📷 Camera | 35s | Contributions |
| 26 | Member 4 on camera + conclusion | 📷 Camera | 45s | Conclusion |

**Total ≈ 20 min 10 sec**

---

## 🛠️ Recording Setup Tips

### Before you record
- Start the Django server: `cd backend && python manage.py runserver`
- Open `http://127.0.0.1:8000/login/` in a **clean browser profile** (no extensions visible)
- Zoom browser to **110%** so text is readable on screen capture
- Use **OBS Studio** or any screen recorder set to **1920×1080 / 60fps**
- Set your webcam to **720p or 1080p**, good lighting on your face
- For code scenes, use **VS Code** with a **light or high-contrast theme** (dark themes can be hard to read in video)

### Recording order recommendation
> You do **not** have to record in scene order. Suggested grouping:

1. **Record all SCREEN scenes first** (Scenes 3–22) in one session with the app running
2. **Record all CAMERA scenes second** (Scenes 1, 2, 23–26) separately with good lighting
3. **Edit in post** — cut screen + camera scenes together with transitions
