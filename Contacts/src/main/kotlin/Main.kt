import kotlin.system.exitProcess
import kotlinx.datetime.*
open class Contact{
    open var name =""
    open val createdDate:String =Clock.System.now().toString()
    open var editedDate: String = Clock.System.now().toString()

}
class Person :Contact(){
    override var name: String =""
    var surname: String =""
    var number: String =""
    var dob: String = "[no data]"
    var gender: String = "[no data]"
    override val createdDate: String =Clock.System.now().toString()
    override var editedDate: String = Clock.System.now().toString()
}
class Organization (override var name: String, var address: String, override val createdDate:String =Clock.System.now().toString(), override var editedDate: String = Clock.System.now().toString()) : Contact()


val contactList = mutableListOf<Person>()

fun add() {
    val newName: String
    val newSurname: String
    var newNumber: String
    val checkNumberFirst = Regex("\\+?\\([\\w]+\\)(([ |-])*[\\w]{2,})*")
    val checkNumberSecond = Regex ("\\+?[\\w]+[ \\|-]\\(([\\w]+\\))(([ \\|-])([\\w]{2,}))*")
    val checkNumberThird = Regex("\\+?[\\w]+([ \\|-]([\\w]){2,})*")
    println("Enter the name: ")
    newName = readln()
    println("Enter the surname: ")
    newSurname = readln()
    println("Enter the number: ")
    newNumber = readln()
    if(!checkNumberFirst.matches(newNumber) && !checkNumberSecond.matches(newNumber) && !checkNumberThird.matches(newNumber)) {
        newNumber = "[no number]"
        println("Wrong number format!")
    }
    val contact = Person()
    contactList.add(contact)
    println("The record added.")
}
fun remove() {
    if (contactList.isEmpty()){
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
    if (contactList.isEmpty()){
        println("No records to edit!")
    } else {
        list()
        println("Select a record: ")
        val index = readln().toInt() - 1
        println("Select a field (name, surname, number) : ")
        val editedField = readln()
        when (editedField) {
            "name" -> {
                println("Enter name: ")
                contactList[index].name = readln()
            }

            "surname" -> {
                println("Enter surname: "); contactList[index].surname = readln()
            }

            "number" -> {
                println("enter number: ");
                val checkNumberFirst = Regex("\\+?\\([\\w]+\\)(([ |-])*[\\w]{2,})*")
                val checkNumberSecond = Regex ("\\+?[\\w]+[ \\|-]\\(([\\w]+\\))(([ \\|-])([\\w]{2,}))*")
                val checkNumberThird = Regex("\\+?[\\w]+([ \\|-]([\\w]){2,})*")
                val newNumber = readln()

                if(!checkNumberFirst.matches(newNumber) && !checkNumberSecond.matches(newNumber) && !checkNumberThird.matches(newNumber)) {
                    contactList[index].number = "[no number]"
                    println("Wrong number format!")
                }else
                contactList[index].number = newNumber
            }
        }
    }

}
fun count() {
    println("The Phone Book has ${contactList.size} records.")
}
fun list() {
    var counter = 1
    for (element in contactList){
        println("${counter}. ${element.name} ${element.surname}, ${element.number}")
         counter++
    }
}
fun exit() {
    exitProcess(0)
}
fun decisionMaker(input: String){
    when(input){
        "add" -> add()
        "remove" -> remove()
        "edit" -> edit()
        "count" -> count()
        "list" -> list()
        "exit" -> exit()

    }
}

fun main() {
    while (true) {
        println("Enter action (add, remove, edit, count, list, exit):")
        decisionMaker(readln())
    }
}
