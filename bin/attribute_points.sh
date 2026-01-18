#!/usr/bin/env bash

# Ensure an argument was provided
if [[ $# -ne 1 ]]; then
  echo "Usage: $0 <positive-integer>"
  exit 1
fi

N="$1"

# Ensure the argument is a positive integer
if ! [[ "$N" =~ ^[0-9]+$ ]] || [[ "$N" -le 0 ]]; then
  echo "Error: argument must be a positive integer"
  exit 1
fi

total=0

for (( i=1; i<=N; i++ )); do
  if (( i % 10 == 0 )); then
    total=$((total + 10))
  elif (( i % 5 == 0 )); then
    total=$((total + 5))
  else
    total=$((total + 2))
  fi
done

echo "$total"