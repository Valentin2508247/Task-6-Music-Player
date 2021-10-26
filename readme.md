# RSShool2021-Android-task6-Music-App

## Цель - реализовать простой музыкальный плеер на Android 🎵

Требования:
- Данные о треках считываются с JSON-файла. Пример файла находится [тут](data/playlist.json). Можете использовать данный или создать свой. JSON-файл добавляем в проект, в assets или res/raw. Необходимо распарсить JSON в список треков и работать с этим списком.  
- Плеер должен проигрывать треки по uri, которые вы получили из JSON. Минимум поддерживаемых контроллов: Play, Pause, PlayNext & PlayPrevious. Плеер показывает данные о текущем треке - название, автор и картинка.
- Если трек играет, приложение показывает нотификацию (MediaStyle) с данными о треке (название, автор, картинка) и контроллами (Play/Pause, PlayNext & PlayPrevious)
- Реализация поддержки rotation (состояние не сбрасывается). Реализация поддержки разных версий layout для portrait и landscape режимов
- Вы должны выбрань одну из архитектур - MVP или MVVM - и реализовать её. View не должно хранить состояния, а получать их из Presenter (MVP) или ViewModel (MVVM) 👆. 

Дополнительные требования:
- Поддержка AudioFocus
- Реализация DI (e.g. Dagger)

![Music Player]
(https://github.com/Valentin2508247/Task-6-Music-Player/blob/master/screenshots/screenshot2.jpg?raw=true) 
![Music Player](https://github.com/Valentin2508247/Task-6-Music-Player/blob/master/screenshots/screenshot.jpg?raw=true) 