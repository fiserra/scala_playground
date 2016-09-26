import org.fiser.json.{ExpressionEvaluator, JsonWriter}

val foo = (1, (3, 3))

JsonWriter.write(foo)

ExpressionEvaluator.evaluate(foo)