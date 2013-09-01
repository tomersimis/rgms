package steps

import rgms.member.Member
import rgms.publication.BookChapter
import rgms.publication.BookChapterController
import rgms.publication.PublicationController

/**
 * Created with IntelliJ IDEA.
 * User: renato
 * To change this template use File | Settings | File Templates.
 */

class BookChapterTestDataAndOperations {
    static bookChapters = [
            [title: "Next Generation Software Product Line Engineering", publicationDate: (new Date("12 October 2012")),
                    publisher: "Person", chapter: 1],
            [title: "SPL Development", publicationDate: (new Date("12 October 2012")),
                    publisher: "Addison", chapter: 5],
            [title: "Artificial Neural Networks", publicationDate: (new Date("25 July 2012")),
                    publisher: "Penguim", chapter: 3]
    ]

    static public def findBookChapterByTitle(String title) {
        bookChapters.find { bookChapter ->
            bookChapter.title == title
        }
    }

    static public boolean bookChapterCompatibleTo(bookChapter, title) {
        def testBookChapter = findBookChapterByTitle(title)
        def compatible = false
        if (testBookChapter == null && bookChapter == null) {
            compatible = true
        } else if (testBookChapter != null && bookChapter != null) {
            compatible = true
            testBookChapter.each { key, data ->
                compatible = compatible && (bookChapter."$key" == data)
            }
        }
        return compatible
    }

    static public void uploadBookChapter(filename) {
        def cont = new BookChapterController()
        def xml = new File(filename);
        def records = new XmlParser()
        cont.saveBookChapters(records.parse(xml));
        cont.response.reset()
    }

    static public void createBookChapter(String title, String filename) {
        def cont = new BookChapterController()
        def date = new Date()
        cont.params << BookChapterTestDataAndOperations.findBookChapterByTitle(title) << [file: filename]
        cont.request.setContent(new byte[1000])
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void removeBookChapter(String title) {
        def testBookChapter = BookChapter.findByTitle(title)
        def cont = new BookChapterController()
        cont.params << [id: testBookChapter.id]
        cont.delete()
    }

    static public boolean containsBookChapter(title, bookList) {
        def testarbook = BookChapter.findByTitle(title)
        def cont = new BookChapterController()
        def result = cont.list().bookChapterInstanceList
        return result.contains(testarbook)
    }

    static public void ShareArticleOnFacebook(String title){
        def member = new Member()
        member.access_token =  "CAAJIlmRWCUwBAN0r1puBTUa4vDZAKxWWlR5gN4qtgZAosBDKGUOLBquyKuHYQ0zxICioiarTJ66mpdZC08U4rHJOrtvXJCB8hMBcLKlQaTdwYZCgMTJtbFnQfIBZAxi6hRIkfw2fCSyCS6DuFIrGRThI53ZCzBOLsZD"
        member.facebook_id = "100006411132660"
        PublicationController.sendPostFacebook(member, title)
    }


}