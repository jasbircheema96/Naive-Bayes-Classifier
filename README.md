# Naive-Bayes-Classifier

Naive Bayes Theorem:-
  P(C=c1/A1=x1,A2=x2,.... An=xn) represents the probability of output class to be c1 if we are given the attributes A1,A2,... An
  Formula:- 
        P(C=c1/A1=x1,A2=x2,.... An=xn) = P(C=c1)* P(A1=x1/C=c1) * P(A2=x2/C=c1) *......... P(An=xn/C=c1)
        Idea behind this formula is that the things on the RHS of the equation can be found with the help of training data,because  the labels for the training data are known.
        The thing on the LHS then predicts the probability of output class C to be c1 during classification.
        Using same formula as above, P(C=c2/A1=x1,......,An=xn) , ... , P(C=cl/A1=x1,.......,An=xn) are found , where l is the number of possible labels
        Then the label with highest probability , ie. highest value of P(C=ci/A1=x1,.....,An=xn) is predicted for the test example

Objective:-
  To classify documents(sentences) into various classes after learning from the training data set

Assumptions:-
  Document consists of only one line                                    (Document considered as a single sentence)
  Training data set is not noisy and does not contain missing attributes (Would be fixed in next version)
  The data file and the corresponding label file must be of same length 
  
Performace:-
  Performance calculated on two separate files:-
  Training file (same file on which the classifier was trained)  -------------   96.5%
  Test file     (unseen entries)                                 --------------  80.2%

