# 🎬 Capstone Showcase Video Script
## Farm Inventory Manager — ADA University Final-Year Project
### Total Runtime: ≈ 20 minutes

---

> **How to use this document**
> Each section shows the recommended duration, talking points (what to say), and
> **[ON SCREEN]** cues (what to show simultaneously). Assign each section to the
> team member responsible for that area. Every member must appear on camera at
> least once — this is a formal requirement.

---

## Section 1 — Project Overview *(≈ 2 minutes)*

### 1.1 The Problem

**Speaker cue (any member, camera on face):**

> "Small and medium-sized farms in Azerbaijan and the wider region face a critical
> operational challenge: farm managers track inventory — seeds, animals, tools,
> farm products — using paper notebooks, spreadsheets, or memory alone. This leads
> to three concrete problems:
>
> First, stock shortages go unnoticed until it is too late to restock.
> Second, there is no integrated view of income, expenses, and stock movement in
> one place.
> Third, language barriers prevent digital adoption — most existing solutions are
> English-only, making them unusable for local farmers."

**[ON SCREEN]:** Show a clean title slide — *"Farm Inventory Manager — Aqrar Ehtiyat İdarəetmə Sistemi"* — followed by a three-bullet problem statement slide.

---

### 1.2 Project Objectives

**Speaker cue:**

> "Our project objective was to design and build a production-ready, web-based
> farm management system that:
>
> - Provides real-time inventory tracking across four asset categories: seeds,
>   animals, tools, and farm products.
> - Automates stock-level monitoring with a configurable alert engine.
> - Supports multi-language use — Azerbaijani, English, and Russian — with full
>   interface translation.
> - Includes a voice transcription feature so users can add stock records by
>   speaking instead of typing.
> - And delivers financial insight through unified expense and income tracking
>   with a calendar-based activity view."

**[ON SCREEN]:** Objectives slide (5 bullets). Transition to a live demo — open the dashboard in the browser so the assessor sees the real application immediately.

---

### 1.3 Application Domain & Context

**Speaker cue:**

> "The application domain is agricultural operations management. Our target users
> are farm owners or managers who may not be highly technical. The application
> runs in a browser and is deployed on a cloud server, with PostgreSQL as the
> production database. It supports multi-user accounts with full per-user data
> isolation."

**[ON SCREEN]:** Show the login screen, then log in as a demo user and land on the dashboard. Keep this visible as you transition to Section 2.

---

## Section 2 — Key Technical Challenges *(≈ 3–4 minutes)*

### 2.1 System Complexity: 14 Loosely-Coupled Django Apps

**Speaker cue:**

> "The application is built with Django 5.2 and is structured into 14 separate
> Django applications: dashboard, inventory, animals, seeds, farm_products, tools,
> expenses, incomes, reports, notifications, suppliers, users, sidebar_menu, and
> sync. Each app has its own models, views, URLs, and migrations.
>
> The main engineering challenge was making these 14 apps work together without
> coupling them tightly. For example, the notification engine in the `notifications`
> app must aggregate stock levels from four completely separate models — Seed,
> Animal, Tool, and FarmProduct — without those models depending on the
> notifications app. We achieved this through a service-layer design pattern."

**[ON SCREEN]:** Show the `backend/apps/` directory tree in the file explorer. Then show the architecture diagram below.

```
┌─────────────────────────────────────────────────────────┐
│                    Django 5.2 Backend                    │
│                                                          │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌────────┐  │
│  │dashboard │  │inventory │  │suppliers │  │reports │  │
│  └──────────┘  └──────────┘  └──────────┘  └────────┘  │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌────────┐  │
│  │  seeds   │  │ animals  │  │  tools   │  │ farm   │  │
│  │          │  │          │  │          │  │products│  │
│  └──────────┘  └──────────┘  └──────────┘  └────────┘  │
│  ┌──────────────────────────────────────────────────┐   │
│  │        notifications  (services.py layer)        │   │
│  │     reads from ↑ all stock apps above ↑          │   │
│  └──────────────────────────────────────────────────┘   │
│  ┌────────┐  ┌────────┐  ┌────────┐  ┌────────────┐    │
│  │expenses│  │incomes │  │  sync  │  │   users    │    │
│  └────────┘  └────────┘  └────────┘  └────────────┘    │
│                                                          │
│              PostgreSQL (prod) / SQLite (test)           │
└─────────────────────────────────────────────────────────┘
```

---

### 2.2 Algorithmic Difficulty: Smart Stock Alert Engine

**Speaker cue:**

> "One of the most algorithmically complex parts of the system is the stock alert
> engine in `notifications/services.py`. The challenge is this: we need to decide
> which of potentially hundreds of inventory items deserve an alert — and at what
> severity level — without requiring the user to manually configure every rule.
>
> We solved this with a three-tier priority approach. First, system-designated
> *important items* — such as wheat seed and animal feed — automatically receive
> default threshold rules based on their measurement unit. Second, users can
> override any threshold with a custom `StockAlertRule` record. Third, items
> explicitly disabled by the user are excluded from all alerting.
>
> The severity is then calculated dynamically: if actual stock falls below 50% of
> the threshold, the item is marked *critical*; otherwise it is *low*. This
> runs every time the dashboard loads and results are cached for 30 seconds."

**[ON SCREEN]:** Show `notifications/services.py` lines 425–468 highlighted — the `build_stock_alerts` function. Point to the `critical_threshold = threshold * Decimal("0.5")` line specifically.

```python
# notifications/services.py — build_stock_alerts (key logic)
threshold = rule.threshold if rule else get_default_threshold_for_item(stock_item)
total = stock_item["total"]
if total > threshold:
    continue  # not an alert — skip

critical_threshold = threshold * Decimal("0.5")
status = "kritik" if total <= critical_threshold else "az"
percentage = int((total / display_max) * 100) if display_max else 0
```

---

### 2.3 Voice Transcription Pipeline

**Speaker cue:**

> "A key usability challenge is that many farm workers are not comfortable typing
> on a keyboard, especially in Azerbaijani. We therefore built a server-side voice
> transcription pipeline. The user presses a microphone button in the add-product
> form, their browser records audio, and the audio blob is uploaded to our Django
> backend. The backend passes it through `faster-whisper`, an optimised Whisper
> implementation, and returns the transcript to pre-fill the form fields.
>
> The engineering challenge here was handling audio format compatibility — browsers
> record WebM/Opus, but Whisper expects WAV or MP3. We use the `av` library as a
> transcoding layer. Additionally, Whisper must be loaded once at startup, not on
> every request, to avoid a 3–4 second cold-start penalty."

**[ON SCREEN]:** Navigate to the add-product page in the running app and demonstrate pressing the microphone button.

---

### 2.4 Multi-Language Architecture

**Speaker cue:**

> "Supporting three languages — Azerbaijani, English, and Russian — across the
> entire interface required us to mark every user-visible string with Django's
> `gettext_lazy` translation wrapper, generate `.po` message files for each
> language, and write custom automation scripts to keep translations up-to-date.
> We also built a `UserLanguageMiddleware` that applies the user's saved language
> preference on every request, overriding the browser's Accept-Language header."

**[ON SCREEN]:** Show the settings page with the language selector. Switch from Azerbaijani to English — let the assessor see the full interface update live.

---

### 2.5 Unit Conversion & Quantity Normalisation

**Speaker cue:**

> "A subtle but important engineering constraint was quantity unit normalisation.
> Seeds can be stored in kg, ton, or grams. Farm products can be in litres or
> millilitres. When computing whether a stock alert threshold is breached, we must
> compare quantities in a common base unit — otherwise 0.5 tonnes would not
> correctly compare against a 25 kg threshold. We implemented a `_convert_farm_qty`
> helper and unit normalisation logic used consistently across the dashboard,
> calendar, and notification systems."

**[ON SCREEN]:** Show `dashboard/views.py` lines 43–54 — the `_convert_farm_qty` function.

---

## Section 3 — Development & Technical Work *(≈ 8–9 minutes)*

### 3.1 System Architecture

**Speaker cue:**

> "Let me walk through the overall system architecture. The user's browser
> communicates with a Django 5.2 WSGI server. All HTML is server-rendered using
> Django's template engine, with static assets — CSS and JavaScript — served by
> WhiteNoise middleware. The data layer is PostgreSQL in production, with SSL
> enforced. For automated tests we switch to an in-memory SQLite database
> automatically, detected via `'test' in sys.argv` at settings load time."

**[ON SCREEN]:** Show the system-level architecture diagram:

```
Browser ──HTTPS──► WhiteNoise (static)
                ► Django 5.2 WSGI App
                     │
                     ├─► PostgreSQL (prod, sslmode=require)
                     ├─► SQLite (test only)
                     ├─► Django Cache (per-view, 120s TTL)
                     └─► faster-whisper (voice transcription)
```

---

### 3.2 Data Models

**Speaker cue:**

> "The relational data model is per-user: every record in every stock table has a
> `created_by` ForeignKey to the Django `User` model, so querying always filters
> by user. There is no tenant isolation at the database level — this is a design
> trade-off we made for simplicity, with the understanding that the system supports
> small teams where one account maps to one farm."

**[ON SCREEN]:** Show a simplified entity relationship diagram:

```
User ─┬─► Seed        (date, item, quantity, unit, price, manual_name)
      ├─► Animal      (date, subcategory, quantity, price, identification_no)
      ├─► Tool        (date, item, quantity, price, manual_name)
      ├─► FarmProduct (date, item, quantity, unit, price, manual_name)
      ├─► Expense     (date, subcategory, amount, title)
      ├─► Income      (date, category, item_name, quantity, amount)
      ├─► Notification (title, category, due_date, is_system_generated, source_key)
      ├─► StockAlertRule (source_type, item_key, threshold, unit, is_active)
      ├─► Supplier    (name, category, phone, rating, last_order_date)
      └─► UserBarcode (code, form_type, target_type, label, metadata)
```

**Speaker cue (continued):**

> "Notice the `StockAlertRule` model — it stores a per-user, per-item threshold
> rule identified by a string `item_key`. Using a string key rather than a
> FK allows rules to cover both catalogue items and free-text manual entries with
> the same model."

---

### 3.3 Dashboard — Weekly Summary & Caching Strategy

**Speaker cue:**

> "The dashboard view aggregates data from six models to produce the weekly
> summary. Rather than running these queries on every page load — which becomes
> expensive as the user's data grows — we implemented a versioned cache key.
> The key includes a `bust_value` that is incremented whenever the user creates
> or modifies a stock record. This means the cache is automatically invalidated
> after any write, without needing to set a short TTL."

**[ON SCREEN]:** Show `dashboard/views.py` lines 761–774:

```python
cache_key = (
    f"dashboard:v6:{user.pk}:{get_dashboard_bust_value(user.pk)}:"
    f"{language_code}:{start_of_week.date().isoformat()}"
)
cached_context = cache.get(cache_key)
if cached_context is not None:
    return render(request, "dashboard/index.html", cached_context)
```

**Speaker cue (continued):**

> "The version tag `v6` in the key also lets us invalidate all existing caches
> globally by bumping this constant — useful when we change the context structure
> during development."

---

### 3.4 Calendar Activity Map — Merging 6 Models into One Timeline

**Speaker cue:**

> "The calendar page is technically the most complex view in the system. It must
> build a day-by-day activity map for the selected month by querying six different
> models — Seeds, Animals, Tools, FarmProducts, Expenses, and Incomes — and merge
> them into a single sorted timeline. Each entry is tagged with a `kind`
> (stock-in, stock-out, income, expense) and a `financial_role` so the calendar
> can compute daily income and expense totals.
>
> A key design decision was the deduplication logic: when an income record was
> auto-created from a stock-out sale, we avoid showing both the stock-out and the
> income entry as two separate calendar items. We detect this by checking for a
> `income:` marker in the `additional_info` field."

**[ON SCREEN]:** Open the calendar page in the running app and click through several days to show the activity panel updating. Then show the `_build_calendar_activity_map` function briefly in the code.

---

### 3.5 Barcode & Scan System

**Speaker cue:**

> "The inventory module includes a barcode generation and scanning system. When a
> farm manager creates a barcode, the system assigns a short unique code with a
> cryptographic signature stored in the `UserBarcode` model. The `signature` field
> uses a `UniqueConstraint` at the database level to prevent duplicate barcodes
> for the same target.
>
> The scan lookup endpoint accepts a barcode code, verifies the signature, and
> returns the target — which could be a form type, a subcategory, an item, or
> manual information. This lets users print barcodes for stock locations and then
> scan them to jump directly to the correct add-stock form."

**[ON SCREEN]:** Show the inventory barcode page. Scan or type a barcode code to demonstrate the lookup. Show the `UserBarcode` model definition.

---

### 3.6 Notification Engine: `sync_stock_alert_notifications`

**Speaker cue:**

> "Each time the user loads a page, the context processor in
> `notifications/context_processors.py` fires and retrieves the notification
> count. This calls `sync_stock_alert_notifications`, which has two
> responsibilities: first, it creates or updates system-generated stock alert
> notifications in the `Notification` table; second, it deletes stale
> system-generated notifications for items that are no longer below threshold.
> This `get_or_create` / stale-delete pattern ensures the notification badge
> always reflects current stock reality without storing redundant data."

**[ON SCREEN]:** Show `notifications/services.py` lines 472–515. Highlight the `get_or_create` call and the `stale_notifications.delete()` call.

---

### 3.7 Supplier Management Module

**Speaker cue:**

> "The suppliers module allows users to maintain a directory of agricultural
> suppliers — seed suppliers, veterinary suppliers, equipment dealers, and so on.
> Each supplier has a category, rating, phone number, and last order date. A
> notable implementation detail is the phone number normalisation: we strip all
> whitespace on save and provide a `formatted_phone` property that reformats
> Azerbaijani numbers to the `+994 XX XXX XX XX` standard automatically."

**[ON SCREEN]:** Navigate to the suppliers page in the app. Show the supplier list and open a supplier detail.

---

### 3.8 Internationalisation Implementation

**Speaker cue:**

> "Internationalisation was implemented using Django's built-in `USE_I18N = True`
> system with `.po` and `.mo` message catalogue files stored in
> `backend/locale/`. We defined three locales: Azerbaijani (`az`), English
> (`en`), and Russian (`ru`). Because many Azerbaijani agricultural terms have no
> standard translation, we wrote a series of automation scripts —
> `append_translations.py`, `auto_translate.py`, and others — to build and
> manage the translation catalogues systematically."

**[ON SCREEN]:** Show the `backend/locale/` directory and one `.po` file opened in a text editor showing `msgid` / `msgstr` pairs.

---

### 3.9 Offline Sync Architecture

**Speaker cue:**

> "The `sync` app exposes push and status endpoints designed to support
> offline-first usage. When connectivity is lost, client-side logic queues changes
> locally. When the connection is restored, the sync process pushes queued records
> to the server. This is an important real-world consideration for farms in areas
> with intermittent internet access."

**[ON SCREEN]:** Show the sync directory and its URL routes briefly.

---

## Section 4 — Results & Achievements *(≈ 3–4 minutes)*

### 4.1 Functional Completeness

**Speaker cue:**

> "The system delivers every feature defined in the project objectives. Let me
> demonstrate the key workflows on the live running application."

**[ON SCREEN — live demo, approximately 90 seconds]:**

| Step | What to show |
|------|-------------|
| 1 | Log into the app as the demo user |
| 2 | Dashboard — point out weekly income, weekly expenses, net balance, stock count cards |
| 3 | Navigate to Seeds — add a seed record with quantity and price |
| 4 | Return to dashboard — show the stock count updated |
| 5 | Navigate to Notifications — show a low-stock alert appearing (or manually set threshold in the Stock Alerts page to trigger one) |
| 6 | Navigate to Suppliers — show the supplier list |
| 7 | Navigate to Calendar — click a date with activity and show the day detail panel |
| 8 | Switch language from Azerbaijani to English — show full UI translation |

---

### 4.2 Stock Alert System: Quantitative Evidence

**Speaker cue:**

> "The stock alert engine covers all four inventory categories. Default thresholds
> are calibrated per measurement unit — for example, 25 kg for seed quantities,
> 10 units for tools, and 20 litres for liquid products. The system supports
> custom user-defined thresholds, and distinguishes two severity levels: 'Low'
> when stock is below 100% of the threshold, and 'Critical' when below 50%.
>
> In testing with realistic farm data, the alert engine correctly identifies and
> prioritises critical items ahead of low items and sorts them by current stock
> level ascending — so the most urgent items always appear first."

**[ON SCREEN]:** Show the Stock Alerts page (Ehtiyat Xəbərdarlığı). Show items sorted by severity. Show one item being toggled to disabled and observe it disappearing from the list.

---

### 4.3 Multi-Language Validation

**Speaker cue:**

> "We validated multi-language support by switching the interface across all three
> supported languages and verifying that every page — including all form labels,
> error messages, navigation items, and data labels — updates correctly. The
> language preference is persisted per user account and applied via our custom
> `UserLanguageMiddleware`, not based on the browser's language setting."

**[ON SCREEN]:** Quickly switch between the three language modes and show the dashboard updating immediately in English, then Russian, then back to Azerbaijani.

---

### 4.4 Testing

**Speaker cue:**

> "We wrote automated tests covering the dashboard view, the notification service,
> the inventory barcode system, and the calendar view logic. Tests run against an
> in-memory SQLite database — the `IS_TEST` flag in `settings.py` automatically
> selects this configuration, so no PostgreSQL connection is needed to run the
> test suite. This makes the CI/CD pipeline fast and self-contained."

**[ON SCREEN]:** Run `python manage.py test` in a terminal (from the `backend/` directory) and show the output with all tests passing.

```
System check identified no issues (0 silenced).
...............
----------------------------------------------------------------------
Ran N tests in X.Xs

OK
```

---

### 4.5 Performance: Caching Effectiveness

**Speaker cue:**

> "We measured the effectiveness of the caching layer by comparing response times
> for the dashboard view with a warm cache versus a cold cache. The warm-cache
> response requires no database queries — the entire context is served from
> Django's cache store. This is critical for the dashboard, which would otherwise
> aggregate data across six models and multiple date ranges on every page load."

**[ON SCREEN]:** Use Django Debug Toolbar or browser DevTools Network tab to show a fast (sub-50ms) cached response versus a slower cold response.

---

## Section 5 — Individual Contributions & Conclusion *(≈ 2–3 minutes)*

> **IMPORTANT:** Each team member must appear on camera for their own segment.
> Fill in the name placeholders below with real team member names and customise
> the contributions to match actual responsibilities.

---

### Team Member 1 — [Name]

**Speaker cue (team member speaks directly to camera):**

> "My primary technical responsibility was the **dashboard and calendar system**.
> I designed and implemented all of the view logic in `dashboard/views.py`, which
> is 1,298 lines and covers the weekly summary, the calendar activity map, and the
> caching strategy.
>
> The component I developed in particular is the `_build_calendar_activity_map`
> function, which merges records from six separate database models into a single
> chronological timeline per day with correct sorting and deduplication.
>
> The main technical challenge I solved was the income-deduplication problem: when
> a stock sale automatically creates an income record, both the stock-out and the
> income would appear as separate calendar events. I designed a marker-based
> detection approach using the `additional_info` field to filter out the
> duplicated entries without requiring schema changes."

---

### Team Member 2 — [Name]

**Speaker cue (team member speaks directly to camera):**

> "My primary responsibility was the **stock alert and notification engine**. I
> built the `notifications` Django app end-to-end — the `StockAlertRule` and
> `Notification` models, the entire `services.py` module (516 lines), and the
> context processor that feeds the notification badge count into every page header.
>
> The core component I developed is the `build_stock_alerts` function, which
> aggregates real-time stock levels across all four inventory categories, applies
> threshold rules, classifies severity, and computes display percentages for the
> progress bars.
>
> The key challenge I solved was the stateless alert sync pattern: rather than
> storing alerts long-term, I implemented a `sync_stock_alert_notifications`
> function that idempotently creates, updates, and deletes system-generated
> notifications on each run. This keeps the notification table always consistent
> with current stock reality."

---

### Team Member 3 — [Name]

**Speaker cue (team member speaks directly to camera):**

> "My responsibility was the **inventory, barcode, and voice transcription
> systems**. I built the `inventory` app with the `ScanItem` and `UserBarcode`
> models, implemented the barcode generation page with its cryptographic signature
> scheme, and integrated the `faster-whisper` voice transcription pipeline into
> the add-product form.
>
> The component I am most proud of is the barcode scan lookup, which resolves a
> scanned code to its target context — form type, subcategory, catalogue item, or
> manual entry — and redirects the user to the correct pre-filled form
> automatically.
>
> The hardest challenge was the audio transcoding step in the voice pipeline.
> Browsers submit WebM/Opus audio, but Whisper requires a compatible audio format.
> I resolved this by adding the `av` library as an in-process transcoding step
> before passing audio to the Whisper model."

---

### Team Member 4 — [Name]

**Speaker cue (team member speaks directly to camera):**

> "My area was the **multi-language system, supplier management, and deployment
> configuration**. I set up the complete Django i18n pipeline — `USE_I18N`,
> `LocaleMiddleware`, locale paths, and the custom `UserLanguageMiddleware` in
> the `common` app.
>
> I also built the full `suppliers` app, including the `Supplier` model with phone
> normalisation and the suppliers list, add, edit, and delete views.
>
> The most technically interesting challenge I solved was keeping translation
> catalogues consistent as the codebase grew. I wrote a set of automation scripts
> (`append_translations.py`, `auto_translate.py`, `compile_translations.py`) that
> systematically extract new translatable strings and merge them into all three
> `.po` files, reducing manual effort significantly."

---

### 5.1 Conclusion

**Speaker cue (all members on camera, one per point):**

> **Member 1:** "The most important technical achievement of this project is the
> unified activity calendar — a single, coherent timeline that aggregates six
> separate data sources with correct financial totals, deduplication, and
> multi-language labels, all delivered under a caching strategy that keeps
> response times fast."
>
> **Member 2:** "From the notification engine perspective, the key achievement is
> a self-maintaining alert system: it needs zero manual administration. It
> automatically promotes important items to monitored status, syncs alerts with
> real stock levels, and expires stale records — all within the normal page-load
> request cycle."
>
> **Member 3:** "The standout achievement on the inventory side is combining barcode
> scanning and voice transcription in a single Django application — two input
> modalities that significantly lower the barrier to entry for non-technical farm
> workers."
>
> **Member 4:** "And from the internationalisation angle, delivering a fully
> translated three-language interface for an agricultural domain — where many terms
> have no standard digital equivalent — required both technical implementation and
> domain research. This is a direct contribution to digital accessibility for
> Azerbaijani farmers."

---

### 5.2 Future Improvements

**Speaker cue:**

> "Looking ahead, we have identified three high-value improvements:
>
> 1. **Mobile application:** The current system is a browser app. A React Native
>    or Flutter mobile client using the existing sync endpoints would make the
>    system fully usable in the field without a laptop.
>
> 2. **Predictive restocking:** Using historical stock consumption data already
>    stored in the database, a simple linear regression or moving-average model
>    could predict when each item will fall below threshold and suggest reorder
>    dates proactively.
>
> 3. **PDF report export:** The reports module currently renders expense summaries
>    in-browser. Adding a `weasyprint` or `reportlab` export would allow users to
>    generate printable annual or monthly financial summaries — a common
>    requirement for farm subsidy applications."

**[ON SCREEN]:** Show a simple roadmap slide with the three items. End with the application dashboard on screen.

---

## ✅ Pre-Recording Checklist

Use this checklist before you start recording:

- [ ] Demo account seeded with realistic farm data (seeds, animals, tools, expenses, incomes)
- [ ] At least one stock item below threshold so the alert badge is visible
- [ ] All three languages tested and switching correctly
- [ ] Voice transcription working (microphone button functional)
- [ ] Barcode scan page loads without errors
- [ ] `python manage.py test` runs and all tests pass
- [ ] Browser is zoomed to 110% so text is clearly visible on screen capture
- [ ] Recording resolution is at least 1080p
- [ ] Each team member has rehearsed their individual section (≈ 45 seconds each)
- [ ] Total script rehearsal is under 20 minutes

---

## 📋 Timing Summary

| Section | Duration | Who |
|---------|----------|-----|
| 1. Project Overview | ≈ 2 min | Any member (rotate) |
| 2. Key Technical Challenges | ≈ 3–4 min | Split across members |
| 3. Development & Technical Work | ≈ 8–9 min | Split by area of ownership |
| 4. Results & Achievements | ≈ 3–4 min | Any member |
| 5. Contributions & Conclusion | ≈ 2–3 min | Each member individually |
| **Total** | **≈ 20 min** | |
