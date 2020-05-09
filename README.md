# Thneed Inc

❗ **Please note that this is a student project that is no longer active. It is kept here just for the historical records.**

Thneed-Inc is a desktop application that allows a business (Thneed Inc.) to keep track of customers and orders. There are Customer objects that indicate the name, address and phone number of the customer, along with a list of current and past orders. There are Order objects which indicate which Customer is placing the order, and the details of the order, such as number of Thneeds, size(s) and color(s). The Order object also indicates the date the order was placed and the date the order was filled (null if not yet filled). There is a GUI which allows the user to view a list of all orders, with the details of the currently selected order shown. If the user clicks on the Customer field for the current order, the details of the Customer are shown. The GUI allows the user to enter a new order, new customer, and update the date a particular order was filled. Finally, the GUI allows the user to save the current state of the data (Orders and Customers) to a file. When the application launches, it looks for that file and populates the application with the information in that file if it exists.

The phase 2 extension lets the program keep track of inventory (current and projected), so the
system can automatically indicate to a customer how much time is left until their order is filled.
It also include a feature to generate reports that indicate how quickly
orders are filled, which items are most/least popular, and which items get backordered, whichsh essentially help the business plan their inventory better in the future
based on the past.


## Built With

Project was written almost entirely in Java and FXML with a little of css to add minimal style to the application.

Detailed documentation can be found in .docx files included in the repository.

## Authors

### Phases 1 and 2 (initial and final)

* **Vladislav Stepanov** - *Order class/Inventory Class/File I/O/Testing* - [vdoriru](https://github.com/vdoriru)
* **Dahlia Aggarwal** - *Inventory class/Customer Class/Report/Defining architecture* - [dahaggar](https://github.iu.edu/dahaggar)
* **Shiqi Shen** - *Debugger/GUI/File I/O/Repo* - [shiqshen](https://github.iu.edu/shiqshen)

### Phase 1 (initial)

* **Yu Li** - [li454](https://github.iu.edu/li454)
* **Delaney Brinson** - [dkbrinso](https://github.iu.edu/dkbrinso)
* **Ted Geis** - [tedgeis](https://github.iu.edu/tedgeis)

## Acknowledgments

Developed as a part of I400 course (Applications Development Java) at Indiana University in December, 2017.
