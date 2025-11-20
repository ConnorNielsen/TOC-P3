#!/bin/bash
iterations=20
total=0

for i in {1..20}; do
    t=$( { /usr/bin/time -p java tm.TMSimulator file5.txt > /dev/null; } 2>&1 \
         | awk '/real/ {print $2}' )
    
    total=$(echo "$total + $t" | bc)
done

average=$(echo "$total / $iterations" | bc -l)

echo "Total:   $total"
echo "Average: $average"
