#!/bin/bash

# Authenticate here:
result=$( curl -sX POST http://localhost:8000/auth/token/ -d username=database_admin -d password=068e363a8a697827994f432e794cc9db16347deb )
# result=$( curl -sX POST http://localhost:8000/auth/token/ -d username=n8kim1 -d password=5bvWZvN8mt1* )
token=$( python3 - <<< "a=$result;print(a['access'])" )

# Actual request here:
curl -vX POST http://localhost:8000/api/match/enqueue/ -d type=tour_scrimmage -d tournament_id=-1 -d player1=917 -d player2=919  --oauth2-bearer $token
