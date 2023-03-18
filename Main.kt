import kotlin.system.exitProcess
import java.time.*
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME


open class Contact {
    open var name = ""
    open var number =""
    open val createdDate: String = LocalDateTime.now().format(ISO_LOCAL_DATE_TIME).toString()
    open var editedDate: String = LocalDateTime.now().format(ISO_LOCAL_DATE_TIME).toString()

}

class Person : Contact() {
    override var name: String = ""
    var surname: String = ""
    override var number: String = ""
    var dob: String = "[no data]"
    var gender: String = "[no data]"
    override val createdDate: String = LocalDateTime.now().format(ISO_LOCAL_DATE_TIME).toString()
    override var editedDate: String = LocalDateTime.now().format(ISO_LOCAL_DATE_TIME).toString()
}

class Organization : Contact() {
    override var name: String = ""
    var address: String = ""
    override var number: String = ""
    override val createdDate: String = LocalDateTime.now().format(ISO_LOCAL_DATE_TIME).toString()
    override var editedDate: String = LocalDateTime.now().format(ISO_LOCAL_DATE_TIME).toString()
}


val contactList = mutableListOf<Contact>()

fun add() {
    println("Enter the type (person, organization): ")
    when (readln()) {
        "person" -> addPerson()
        "organization" -> addOrganization()
    }
}
fun list() {
    var counter = 1
    for (element in contactList) {
        if (element is Person) {
            println("${counter}. ${element.name} ${element.surname}")
        } else {
            println("${counter}. ${element.name}")
        }
        counter++
    }
}

fun numberChecker(number: String): String {
    var newNumber = number
    val checkNumberFirst = Regex("\\+?\\([\\w]+\\)(([ |-])*[\\w]{2,})*")
    val checkNumberSecond = Regex("\\+?[\\w]+[ \\|-]\\(([\\w]+\\))(([ \\|-])([\\w]{2,}))*")
    val checkNumberThird = Regex("\\+?[\\w]+([ \\|-]([\\w]){2,})*")
    if (!checkNumberFirst.matches(newNumber) && !checkNumberSecond.matches(newNumber) && !checkNumberThird.matches(
            newNumber
        )
    ) {
        newNumber = "[no number]"
        println("Wrong number format!")
        return newNumber
    }
    return newNumber
}
fun dobChecker(date: String): String{
    val regex = Regex("[0-9]+")
    if (!date.matches(regex)){
        println("Bad birth date!")
        return "[no data]"
    } else return date
}
fun genderBlender(gender: String): String{
    if (gender.uppercase() == "M" || gender.uppercase() == "F"){
        return gender
    } else {
        println("Bad gender!")
        return "[no data]"
    }
}

fun addPerson() {
    val person = Person()
    var newNumber: String
    println("Enter the name: ")
    person.name = readln()
    println("Enter the surname: ")
    person.surname = readln()
    println("Enter the birth date: ")
    person.dob = dobChecker(readln())
    println("Enter the gender (M, F): ")
    person.gender = genderBlender(readln())
    println("Enter the number: ")
    newNumber = numberChecker(readln())
    person.number = newNumber
    contactList.add(person)
    println("The record added.")
}

fun addOrganization() {
    val organization = Organization()
    var newNumber: String
    println("Enter the organization name: ")
    organization.name = readln()
    println("Enter the address: ")
    organization.address = readln()
    println("Enter the number: ")
    newNumber = numberChecker(readln())
    organization.number = newNumber
    contactList.add(organization)
    println("The record added.")
}

fun remove() {
    if (contactList.isEmpty()) {
        println("No records to remove!")
    } else {
        list()
        println("Select a record: ")
        val index = readln().toInt() - 1
        contactList.removeAt(index)
        println("The record removed!")
    }
}

fun edit() {
    if (contactList.isEmpty()) {
        println("No records to edit!")
    } else {
        list()
        println("Select a record: ")
        val index = readln().toInt() - 1
        if (contactList[index] is Person) {
            val person = contactList[index] as Person
            println("Select a field (name, surname, birth, gender, number) : ")
            val editedField = readln()
            when (editedField) {
                "name" -> {
                    println("Enter name: ")
                    person.name = readln()
                    person.editedDate = LocalDateTime.now().format(ISO_LOCAL_DATE_TIME).toString()
                }
                "gender" -> {
                    println("Enter gender: ")
                    person.gender = genderBlender(readln())
                    person.editedDate = LocalDateTime.now().format(ISO_LOCAL_DATE_TIME).toString()
                }

                "surname" -> {
                    println("Enter surname: ")
                    person.surname = readln()
                    contactList[index].editedDate = LocalDateTime.now().format(ISO_LOCAL_DATE_TIME).toString()
                }

                "number" -> {
                    println("enter number: ");
                    val newNumber = numberChecker(readln())
                    person.number = newNumber
                    contactList[index].editedDate = LocalDateTime.now().format(ISO_LOCAL_DATE_TIME).toString()
                }
            }
        }
        if (contactList[index] is Organization) {
            val organization = contactList[index] as Organization
            println("Select a field (address, number): ")
            when (readln()) {
                "address" -> {
                    println("Enter address: ")
                    organization.address = readln()
                    contactList[index].editedDate = LocalDateTime.now().format(ISO_LOCAL_DATE_TIME).toString()
                }
                "number" -> { println("Enter number: ")
                    val newNumber = numberChecker(readln())
                    organization.number = newNumber
                    contactList[index].editedDate = LocalDateTime.now().format(ISO_LOCAL_DATE_TIME).toString()
                }

            }
        }
    }
}

fun count() {
    println("The Phone Book has ${contactList.size} records.")
}
fun info(){
    list()
    println("Enter index to show info: ")
    val index = readln().toInt() - 1
    if (contactList[index] is Person){
        val person = contactList[index] as Person
        println("Name: ${person.name}\nSurname: ${person.surname}\n" +
                "Birth date: ${person.dob}\nGender: ${person.gender}\n" +
                "Number: ${person.number}\nTime created: ${person.createdDate}\nTime last edit: ${person.editedDate}")
    }
    if (contactList[index] is Organization){
        val organization = contactList[index] as Organization
        println("Organization name: ${organization.name}\nAddress: ${organization.address}\nNumber: ${organization.number}\n" +
                "Time created: ${organization.createdDate}\nTime last edit: ${organization.editedDate}")
    }
}

fun exit() {
    exitProcess(0)
}

fun decisionMaker(input: String) {
    when (input) {
        "add" -> add()
        "remove" -> remove()
        "edit" -> edit()
        "count" -> count()
        "info" -> info()
        "exit" -> exit()

    }
}

fun main() {
    var firstIteration = true
    while (true) {
        if (firstIteration) {
            println("Enter action (add, remove, edit, count, info, exit):")
            decisionMaker(readln())
            firstIteration = false
        } else {
            println("\nEnter action (add, remove, edit, count, info, exit):")
            decisionMaker(readln())
        }
    }
}

