runWithProfiler.sh "./run.sh batch -u"
python -m http.server --bind 0.0.0.0 --directory ./resultado_profiler/ 7002 &
