set version=2.0.8

git add .

git commit -m "update"

git tag %version%

git push origin %version%

start https://jitpack.io/#housong12590/FastLib