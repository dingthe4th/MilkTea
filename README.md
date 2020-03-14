@Authors
  Ding Bayeta IV
  Teddy Benito II
  Joseph Portugal
  Isaiah Tupal

CURRENT TODOS:
  1. Make the cashier mode environment work.
      If item is clicked, then a window will pop-up showing that item.
          The cashier (hereinafter referred to as user) can choose the ff:
                a. size of drink      | S / M / L
                b. sugar level        | 0 - 100
                c. amount of ice      | no ice - many ice
                d. add - ons          | 
                e. can avail discount | for PWD/Senior citizen
      Each order details must be stored for statistics purposes.
      
  2. Make the statistics screen mode work.

# MilkTea
A point of sale system for the business Taste from the Greens. A project for the completion of the requirements in the course software design and engineering (SOFDDESG).

Initially the idea is to model the system based from the client current system.
However, we want the project to be editable.

The user can edit (meaning add / edit / delete) a given item registered in the system.

Given the class:
  Item
    name
    type
    price
    imagepath
    image

In the current system, the developers (of that system) were given the list of items that are available in the store.
But a problem arises, what if THAT item is not already available / removed from the store items?
The user will contact the developer to update it every time this issue occurs, and we don't want that. It's a waste of
money.
  
