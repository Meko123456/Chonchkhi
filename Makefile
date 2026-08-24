.PHONY: build test lint clean format check

build:          ## Assemble the debug APK
	./gradlew :app:assembleDebug

test:           ## Run unit tests
	./gradlew test

lint:           ## Run Android lint
	./gradlew :app:lintDebug

check:          ## Build + lint + tests
	./gradlew :app:assembleDebug :app:lintDebug test

format:         ## Apply Spotless formatting (if configured)
	./gradlew spotlessApply

clean:          ## Clean build outputs
	./gradlew clean
