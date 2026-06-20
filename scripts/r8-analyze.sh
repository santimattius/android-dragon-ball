#!/usr/bin/env bash
# Genera reportes HTML del R8 Configuration Analyzer para cada tag del demo.
#
# Uso:
#   ./scripts/r8-analyze.sh              # analiza los 3 tags del demo
#   ./scripts/r8-analyze.sh 01 02        # analiza solo los tags indicados (00, 01, 02)
#
# Requisitos:
#   - R8 9.3.7-dev pinned en settings.gradle.kts (ya configurado)
#   - Sin cambios locales sin commitear (hace checkout de cada tag)
#
# Output: build/r8-analyzer-reports/<tag>/configanalyzer.html

set -euo pipefail

REPO_DIR="$(cd "$(dirname "$0")/.." && pwd)"
OUTPUT_DIR="$REPO_DIR/build/r8-analyzer-reports"
CURRENT_REF=$(git -C "$REPO_DIR" symbolic-ref --short HEAD 2>/dev/null || git -C "$REPO_DIR" rev-parse --short HEAD)

declare -A TAGS=(
  ["00"]="r8-demo/00-baseline"
  ["01"]="r8-demo/01-broad-rule"
  ["02"]="r8-demo/02-surgical-rule"
)

# Filtrar por argumentos si se pasan (ej: ./r8-analyze.sh 01 02)
if [[ $# -gt 0 ]]; then
  SELECTED=("$@")
else
  SELECTED=("00" "01" "02")
fi

# Verificar que no hay cambios sin commitear
if ! git -C "$REPO_DIR" diff --quiet || ! git -C "$REPO_DIR" diff --cached --quiet; then
  echo "ERROR: hay cambios sin commitear. Hacé commit o stash antes de correr este script."
  exit 1
fi

echo "R8 Configuration Analyzer — generando reportes"
echo "Repo: $REPO_DIR"
echo "Output: $OUTPUT_DIR"
echo ""

GENERATED=()

for KEY in "${SELECTED[@]}"; do
  TAG="${TAGS[$KEY]:-}"
  if [[ -z "$TAG" ]]; then
    echo "⚠ Tag '$KEY' no reconocido. Opciones: 00, 01, 02"
    continue
  fi

  NAME="${TAG//\//-}"
  REPORT_DIR="$OUTPUT_DIR/$NAME"
  mkdir -p "$REPORT_DIR"

  echo "→ [$TAG]"
  git -C "$REPO_DIR" checkout --quiet "$TAG"

  echo "  Ensamblando build benchmark con R8..."
  "$REPO_DIR/gradlew" -p "$REPO_DIR" :app:assembleBenchmark \
    "-Dcom.android.tools.r8.dumpkeepradiushtmltodirectory=$REPORT_DIR" \
    --quiet --no-daemon

  HTML=$(ls "$REPORT_DIR"/*.html 2>/dev/null | head -1 || true)
  if [[ -n "$HTML" ]]; then
    echo "  ✓ Reporte: $HTML"
    GENERATED+=("$HTML")
  else
    echo "  ✗ No se generó reporte — verificá que R8 9.3.7-dev esté activo"
  fi
  echo ""
done

echo "→ Restaurando $CURRENT_REF..."
git -C "$REPO_DIR" checkout --quiet "$CURRENT_REF"
echo ""

if [[ ${#GENERATED[@]} -eq 0 ]]; then
  echo "No se generó ningún reporte."
  exit 1
fi

echo "Reportes generados:"
for F in "${GENERATED[@]}"; do
  echo "  $F"
done
echo ""

# Abrir en navegador (macOS)
if [[ "$OSTYPE" == "darwin"* ]]; then
  echo "Abriendo reportes en el navegador..."
  for F in "${GENERATED[@]}"; do
    open "$F"
  done
fi
