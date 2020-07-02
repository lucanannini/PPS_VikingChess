import actor_ia.MoveGenerator
import ia.EvaluationFunction
import model._
import org.scalatest.FunSuite
import utils.BoardGame.Board
import utils.{Coordinate, Move}

class EvaluationFunctionTest extends FunSuite {

  val THEORY: String = TheoryGame.GameRules.toString
  val prolog: ParserProlog = ParserPrologImpl(THEORY)
  var snapshot: GameSnapshot = _
  var game: (Player.Val, Player.Val, Board, Int) = _

  test("Tests Score King Near Corner - Brandubh."){
    game = prolog.createGame(GameVariant.Brandubh.toString().toLowerCase)
    snapshot = GameSnapshot(GameVariant.Brandubh, game._1, game._2, game._3, Option.empty, 0, 0)

    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(4,6),Coordinate(3,6)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(4,5),Coordinate(1,5)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(4,7),Coordinate(2,7)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(4,4),Coordinate(4,6)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(3,6),Coordinate(1,6)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(4,6),Coordinate(7,6)))

    assert(EvaluationFunction.score(snapshot).equals(900))
  }

  test("Tests Score King To Corner In One - Brandubh."){
    game = prolog.createGame(GameVariant.Brandubh.toString().toLowerCase)
    snapshot = GameSnapshot(GameVariant.Brandubh, game._1, game._2, game._3, Option.empty, 0, 0)

    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(4,6),Coordinate(3,6)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(4,5),Coordinate(1,5)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(4,7),Coordinate(2,7)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(4,4),Coordinate(4,7)))

    assert(EvaluationFunction.score(snapshot).equals(900))
  }

  test("Tests Score Capture King In One On Throne - Tablut."){
    game = prolog.createGame(GameVariant.Tablut.toString().toLowerCase)
    snapshot = GameSnapshot(GameVariant.Tablut, game._1, game._2, game._3, Option.empty, 0, 0)

    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(5,2), Coordinate(1,2)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(5,3), Coordinate(2,3)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(5,1), Coordinate(5,2)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(5,4), Coordinate(2,4)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(5,2), Coordinate(5,4)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(4,5), Coordinate(4,2)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(4,9), Coordinate(4,5)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(5,6), Coordinate(2,6)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(9,6), Coordinate(5,6)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(6,5), Coordinate(6,8)))


    assert(EvaluationFunction.score(snapshot) == -900)
  }

  test("Tests Score Capture King Near Throne - Tablut."){
    game = prolog.createGame(GameVariant.Tablut.toString().toLowerCase)
    snapshot = GameSnapshot(GameVariant.Tablut, game._1, game._2, game._3, Option.empty, 0, 0)

    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(5,2), Coordinate(2,2)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(5,3), Coordinate(1,3)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(5,1), Coordinate(5,3)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(5,4), Coordinate(2,4)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(9,4), Coordinate(6,4)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(5,5), Coordinate(5,4)))

    assert(EvaluationFunction.score(snapshot) == -900)
  }

  test("Test Score Capture King Far From Throne - Brandubh") {
    game = prolog.createGame(GameVariant.Brandubh.toString().toLowerCase)
    snapshot = GameSnapshot(GameVariant.Tablut, game._1, game._2, game._3, Option.empty, 0, 0)

    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(6,4), Coordinate(6,2)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(5,4), Coordinate(5,6)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(4,2), Coordinate(5,2)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(4,4), Coordinate(6,4)))

    assert(EvaluationFunction.score(snapshot) == -900)
  }

  test("Test Score Capture King Far From Throne - Tawlbwurdd") {
    game = prolog.createGame(GameVariant.Tawlbwrdd.toString().toLowerCase)
    snapshot = GameSnapshot(GameVariant.Tawlbwrdd, game._1, game._2, game._3, Option.empty, 0, 0)

    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(9,6), Coordinate(9,2)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(8,6), Coordinate(8,1)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(9,2), Coordinate(9,3)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(7,5), Coordinate(7,3)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(9,3), Coordinate(9,2)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(7,6), Coordinate(7,4)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(9,2), Coordinate(9,3)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(6,6), Coordinate(10,6)))

    assert(EvaluationFunction.score(snapshot) == -900)
  }

  test("Test Score Black Ahead - Brandubh") {
    game = prolog.createGame(GameVariant.Brandubh.toString().toLowerCase)
    snapshot = GameSnapshot(GameVariant.Brandubh, game._1, game._2, game._3, Option.empty, 0, 0)

    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(2,4), Coordinate(2,7)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(3,4), Coordinate(3,1)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(4,2), Coordinate(3,2)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(4,3), Coordinate(3,3)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(4,1), Coordinate(4,3)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(5,4), Coordinate(5,3)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(4,6), Coordinate(3,6)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(5,3), Coordinate(5,2)))

    assert(EvaluationFunction.score(snapshot) < 0)
  }

  test("Test Score White Ahead To Rows And Columns Checked - Hnefatafl") {
    game = prolog.createGame(GameVariant.Hnefatafl.toString().toLowerCase)
    snapshot = GameSnapshot(GameVariant.Hnefatafl, game._1, game._2, game._3, Option.empty, 0, 0)

    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(7,1), Coordinate(7,4)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(8,6), Coordinate(8,4)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(5,1), Coordinate(5,4)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(4,6), Coordinate(4,4)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(5,11), Coordinate(5,8)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(4,4), Coordinate(4,8)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(6,2), Coordinate(5,2)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(6,4), Coordinate(3,4)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(5,2), Coordinate(3,2)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(6,5), Coordinate(6,2)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(3,2), Coordinate(5,2)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(6,6), Coordinate(6,3)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(8,1), Coordinate(8,2)))

    assert(EvaluationFunction.score(snapshot) > 50)
  }

  test("Test Score Black Surround King On Three Side - Tawlbwrdd") {
    game = prolog.createGame(GameVariant.Tawlbwrdd.toString().toLowerCase)
    snapshot = GameSnapshot(GameVariant.Tawlbwrdd, game._1, game._2, game._3, Option.empty, 0, 0)

    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(6,3), Coordinate(4,3)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(6,4), Coordinate(3,4)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(4,3), Coordinate(4,4)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(6,5), Coordinate(6,3)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(2,5), Coordinate(2,4)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(6,3), Coordinate(9,3)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(7,1), Coordinate(9,1)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(6,6), Coordinate(6,2)))

    assert(EvaluationFunction.score(snapshot) < -100)
  }

  test("Test Score Captured Pieces (4 whites / 2 blacks) - Hnefatafl") {
    game = prolog.createGame(GameVariant.Hnefatafl.toString().toLowerCase)
    snapshot = GameSnapshot(GameVariant.Hnefatafl, game._1, game._2, game._3, Option.empty, 0, 0)
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(6,2), Coordinate(3,2)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(6,4), Coordinate(2,4)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(3,2), Coordinate(3,4)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(4,6), Coordinate(3,6)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(4,11), Coordinate(4,7)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(3,6), Coordinate(3,7)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(2,6), Coordinate(4,6)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(3,7), Coordinate(3,5)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(4,6), Coordinate(3,6)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(5,5), Coordinate(3,5)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(1,8), Coordinate(3,8)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(5,7), Coordinate(3,7)))
    snapshot = MoveGenerator.makeMove(snapshot, Move(Coordinate(1,6), Coordinate(3,6)))

    assert(EvaluationFunction.scoreCapturedBlack(snapshot) == 20 &&
      EvaluationFunction.scoreCapturedWhite(snapshot) == 60)
  }

}
