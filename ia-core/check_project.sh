#!/bin/bash
# Checklist automatizado de padronização para o projeto Biblia
set -e

# 1. Build e testes
mvn clean verify

# 2. Checkstyle
mvn checkstyle:check

# 3. PMD
mvn pmd:check

# 4. SpotBugs
mvn spotbugs:check

# 5. JaCoCo (cobertura de testes)
mvn jacoco:report

# 6. Verificação de README e CONTRIBUTING
for dir in $(find . -type d -maxdepth 2); do
  if [ -d "$dir" ]; then
    if [ ! -f "$dir/README.md" ]; then
      echo "[FALTA] $dir/README.md"
    fi
    if [ ! -f "$dir/CONTRIBUTING.md" ]; then
      echo "[FALTA] $dir/CONTRIBUTING.md"
    fi
  fi
done

echo "Checklist concluído. Corrija os itens faltantes acima."
