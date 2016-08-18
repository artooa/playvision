import org.specs2.mutable.SpecificationWithJUnit
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner


@RunWith(classOf[JUnitRunner])
class ModelsTestSuite extends SpecificationWithJUnit {
  import models._
  
 // -- Date helpers
  
  def dateIs(date: java.util.Date, str: String) = new java.text.SimpleDateFormat("yyyy-MM-dd").format(date) == str
  
  // --
  
//  "Menu" should {
//    
//    "be retrieved by id" in {
//      running(FakeApplication() {
//        
        val selection = Menu.Home
      
        selection.toString() must equalTo("Home")
  		println("Hallo from Testing")
        
//      }
//    }
    
    
  "The 'Hello world' string" should {
      "contain 11 characters" in {
        "Hello world" must have size(11)
      }
      "start with 'Hello'" in {
        "Hello world" must startWith("Hello")
      }
      "end with 'world'" in {
        "Hello world" must endWith("world")
      }
    }

  		
}