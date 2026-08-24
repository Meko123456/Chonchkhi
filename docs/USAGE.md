# Adopting Chonchkhi

1. **Create your repo** from this template (GitHub → *Use this template*), or clone it.
2. **Rename the package** `io.github.meko123456.chonchkhi` to your own across `app/` and `core/`
   (namespaces in the `build.gradle.kts` files + the source directories).
3. **Change the `applicationId`** in `app/build.gradle.kts`.
4. **Rename the app** in `app/src/main/res/values/strings.xml`.
5. Add feature modules next to `:core` and register them in `settings.gradle.kts`.

## Module layout

- `:app` — the Android application (Compose UI only; no business logic).
- `:core` — pure/business logic as an Android library, unit-tested on the JVM.
- add `:feature-*` modules per feature, each depending on `:core`.

## Everyday commands

```bash
make build   # assemble debug
make test    # unit tests
make lint    # android lint
make check   # all of the above
```
