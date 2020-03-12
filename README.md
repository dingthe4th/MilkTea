CURRENT TODOS:
  1. Image is not showing if you run the program
      Current fix (but not working)
          set a 'resource' folder with the same rank as 'src' folder
          and assign it as RESOURCE folder in Project Settings - Modules - Sources - Resources
  2. Load item from .txt or .xlsx file

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
  
