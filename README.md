In development...

Example game .yml:
```yml
name: TestGame
world: world
min_players: 1
max_players: 16
replay: TestGameBridge1
map_center:
  x: 0
  y: 0
  z: 0
red_team:
  reflect: '0'
  spawn:
    x: 0
    y: 0
    z: 0
green_team:
  reflect: '90'
  spawn:
    x: 0
    y: 0
    z: 0
blue_team:
  reflect: '180'
  spawn:
    x: 0
    y: 0
    z: 0
yellow_team:
  reflect: '270'
  spawn:
    x: 0
    y: 0
    z: 0
```

TODO:
- optimize load process (FROM DB)