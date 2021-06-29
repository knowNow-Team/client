![issue_badge](https://img.shields.io/github/contributors/knowNow-Team/client)
![issue_badge](https://img.shields.io/github/license/knowNow-Team/client)
![issue_badge](https://img.shields.io/github/languages/top/knowNow-Team/client)

# 📝 KnowNow

![1](https://user-images.githubusercontent.com/55980680/123798069-ceb44180-d921-11eb-9956-381583df46d0.png)  |   ![2](https://user-images.githubusercontent.com/55980680/123798074-cf4cd800-d921-11eb-9c65-eaf9456cff3a.png) | ![3](https://user-images.githubusercontent.com/55980680/123798077-cfe56e80-d921-11eb-97ce-7ddecd18cd97.png) |
:-------------------------:|:-------------------------:|:-------------------------:
![alarm](https://user-images.githubusercontent.com/55980680/123798060-cd831480-d921-11eb-9f3e-7ea0bfba6bd4.png)  |   ![wordBook](https://user-images.githubusercontent.com/55980680/123798081-cfe56e80-d921-11eb-9fae-a35b41ff9b4d.png) | ![4](https://user-images.githubusercontent.com/55980680/123798050-ca882400-d921-11eb-9315-9e22ccc0e59b.png) 

> 단어암기를 도와주는 앱 서비스 '**KnowNow**'입니다.
>
> 1️⃣ 이미지와 텍스트에서 단어를 추출하고 단어의 의미와 품사,발음기호를 보여줍니다.
>
> 2️⃣ 자신의 단어장을 만들어 단어를 추가하고 받아쓰기,퍼즐 맞추기를 통해 테스트를 볼 수 있습니다.
>
> 3️⃣ 친구코드를 통해 친구를 맺어 매주 새로운 랭킹을 확인합니다.
>
> 4️⃣ 알림을 설정하여 원하는 시간대에 푸시알림을 받아 단어 암기를 할 수 있습니다.

<br>

`프로젝트 기간 2021.03 ~ 2021.05`
[`knowNow-notion`](https://www.notion.so/knowNow-7b40919b365e4356bf1960409f26215a)



<br>

## 📌 Technology Stack

- **아키텍처**  -  MVVM, Observer Pattern
- **네트워크(NetWork)**  -  Retrofit2 , Okhttp3
- **내부 데이터베이스 (Local DataBase)**  -  Room `ver 2.2.5`
- **(디자인 (Design)**  -  material, lottie
- **other** - RecyclerView,ViewPager,seekBar


<br>

## ⭐ Tool

- Andoriod Studio
- Postman, Swagger
- GitBash, GitDeskTop
- Figma

<br>

## 🌳 Diretory

```
data |- local  |- dao 
               |- entity
               |- database
               
     |- remote |- dto
               |- api
               |- RetrofitClient
ui   |- adapter 
     |- view |- MainActivity
             |- home |- HomeFragment
                     |- ...
             |- ...
utils
viewmodel 
```

<br>


<br>


## 👩‍💻 Team, role

### 클라이언트

- **[김재원](https://github.com/ashwon12)** - 로그인, 홈, 단어장, 세부설정, 마이페이지, 친구관리, 의견보내기
- **[이민환](https://github.com/minhvvan)** - 테스트설정, 테스트, 테스트기록, 단어추가, 랭킹, 단어 알림, 도움말


### 서버 - [server Repository](https://github.com/knowNow-Team)
- **[주성민](https://github.com/god9599)** 
- **[강영우](https://github.com/rdd9223)** 
- **[이상혁](https://github.com/ksshlee)** 


### 아키텍처
![아키텍처](https://user-images.githubusercontent.com/55980680/123812194-da5a3500-d92e-11eb-805a-759b262a7fd0.png)


