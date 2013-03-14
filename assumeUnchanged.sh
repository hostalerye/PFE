#!/bin/bash

git ls-files cluster/.idea/ | xargs git update-index --assume-unchanged 
git ls-files cluster/.idea_modules/ | xargs git update-index --assume-unchanged
git ls-files cluster/project/target/ | xargs git update-index --assume-unchanged
git ls-files cluster/project/project/target/ | xargs git update-index --assume-unchanged
git ls-files cluster/target/ | xargs git update-index --assume-unchanged
git ls-files cluster/tests/target/ | xargs git update-index --assume-unchanged

