############################################
Author: Qian Tang & Ching Man Lee
Application: Game of Emotion
############################################

There are four main tables in the MySQL database in this project:

1. USER_ACCOUNT
  The table shows all the user account information, including username, password and model_num.
  Model_num is used to identify which of the self-diagnostic model and corresponding video is assigned to the user.
  In this project, model_num as 0 refers to the affective circumplex model and 1 for the body map model.

2. EMOTION_RATING
  The table shows all the records in regards to the user emotion rating. Each record includes username, 
  rating score and created time.

3. AFFECTIVECIRCUMPLEX_MODEL
  The table shows all the records in regards to the user input in the affective circumplex model. 
  Each record includes username, vertical/horizontal position of spot, radius of the spot and the created time of the record.

4. BODYMAP_MODEL
  The table shows all the records in regards to the user input in the body map modell. Each record includes username, 
  type of icon, vertical/horizontal position of the icon and the created time of the record.
  *Note: ID with the same created time indicate they are on the test in one submission, 
         as users can use at most 10 icons per submission.
