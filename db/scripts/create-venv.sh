#!/bin/sh

if [ -d ".venv" ]; then
    echo ".venv already exists."
    echo -e "If it doesnt work, try running:\n\tsource .venv/bin/activate\n"
    echo -e "If you want to deactivate, simply run\n\tdeactivate\n"
    exit 0
fi


python -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt

echo -e "reopen your terminal or run:\n\nsource .venv/bin/activate\n"