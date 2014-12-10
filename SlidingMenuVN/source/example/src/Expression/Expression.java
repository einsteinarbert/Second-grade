package Expression;

import java.util.LinkedList;
import java.util.Stack;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("NewApi") public class Expression {
	// private variables
	private String expression;
	private Randomize rd;
	private final double EPXILON = (double) 0.000000001;
	private final double CAN_TREN = 1500;
	private final double CAN_DUOI = -1480;
	private final double LAGGING = 400;
	private double dx = 1;
	private int min, max, expResult;
	private int antiLag = 0;
	// public functions
	public Expression() {
		rd = new Randomize();
		rd.setRangeValue(1, 10);
		min = 1;
		max = 10;
		expResult = 5;
	}
	// hieu: sua tam min max
	public void setLimited(int min, int max, int result) {
		this.min = min;
		this.max = max;
		this.expResult = result;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	public int getExpectedResult() {
		return expResult;
	}
	public double caculatorExpression(String _expression){
		Stack<String> postfix = infix2Postfix(_expression);
		return calcPostfix(postfix);
	}
	
	public String createExpression(String expTemplate) throws InterruptedException{// A+B-C
		expression = expTemplate;
		String gener = "";
		while(true){
			gener = getExp(expTemplate, min, max, expResult);
			if (gener.equals("error")) {
				if(antiLag<LAGGING){
					antiLag++;
					continue;
				}else{
					antiLag=0;
					return "error";
				}
			}
			gener = formatInfixExpression(gener);
			if (Math.abs((calcPostfix((infix2Postfix(gener))) - expResult)) < EPXILON) {
				break;
			}
			Thread.sleep(5);
		}
		return gener;
	}
	// get variables list
	public String getExp(String inputStr, int min, int max, int expectedResult) {
		inputStr = formatInfixExpression(inputStr);
		Stack<String> pfx = infix2Postfix(inputStr);
		int size = pfx.size();
		LinkedList<String> varList = new LinkedList<String>();
		String postfixStr = "";
		for (int j = 0; j < size; j++) {
			postfixStr += pfx.get(j);
		}
		size = postfixStr.length();
		int len;
		for (int j = 0; j < size; j++) {
			char intance = postfixStr.charAt(j);
			if (intance >= 'A' && intance <= 'Z') {
				len = varList.size();
				boolean not2var = true;
				for (int j2 = 0; j2 < len; j2++) {
					if (intance == varList.get(j2).charAt(0)) {
						not2var = false;
					}
				}
				if (not2var) {
					varList.push("" + intance);
				}
			}
		}
		varList = selectionSort(varList);// got varList;
		size = varList.size();
		String simu = solveSimulator(pfx, varList, min, max, expectedResult);
		// //Log.e("Bieu thuc cuoi cung", "kqla: " + simu.toString());
		return simu;
	}

	public double calcPostfix(Stack<String> buf) {// Calculating on Natural-N
		// (not in
		// Real-R)
		/*
		 * if (buf.isEmpty()) return -999;
		 */
		Stack<Double> resultStack = new Stack<Double>();
		// //Log.e("Bieu thuc", buf.toString());
		char child = '1';
		String buffer = "";
		double x = 1, y = 1;
		int len = buf.size();
		// //Log.e("LENGTH", len+"|");
		for (int i = 0; i < len; i++) {
			buffer = buf.get(i);
			buffer = buffer.replace(" ", "");
			// //Log.e("Stack", resultStack.toString()+"|"+buffer+"|"+i);
			if (IsOperand(buffer)) {// 0~9
				resultStack.push(Double.parseDouble(buffer));// loi
			} else {
				child = buffer.charAt(0);

				// //Log.e("Child", child+"}");
				switch (child) {
				case '+':
					// //Log.e("LOI+?",
					// resultStack.toString()+"|"+buf.toString());
					x = resultStack.pop();
					y = resultStack.pop();
					resultStack.push(y + x);
					break;
				case '-':
					// //Log.e("LOI-?",
					// resultStack.toString()+"|"+buf.toString());
					x = resultStack.pop();
					y = resultStack.pop();
					resultStack.push(y - x);
					break;
				case '*':
				case 'x':
					// //Log.e("LOIxxx?",
					// resultStack.toString()+"|"+buf.toString());
					x = resultStack.pop();
					y = resultStack.pop();
					// resultStack.push((double) Math.round((y * x)));
					resultStack.push(y * x);
					break;
				case '/':
					// //Log.e("LOI O DAY AK?",
					// resultStack.toString()+"|"+buf.toString());
					x = resultStack.pop();
					y = resultStack.pop();
					// resultStack.push((double) Math.round((y / x)));
					resultStack.push(y / x);
					break;
				case '%':
					x = resultStack.pop();
					y = resultStack.pop();
					resultStack.push(y % x);
					break;
				}
			}
		}
		double rt = resultStack.pop();
		// //Log.e("Caculated", "" + rt);
		return rt;
	}

	public Stack<String> infix2Postfix(String infix) {
		infix = formatInfixExpression(infix);
		Stack<String> stack = new Stack<String>();
		Stack<String> postfix = new Stack<String>();
		int len = infix.length();
		String s;
		String number = "";
		for (int i = 0; i < len; i++) {
			s = "" + infix.charAt(i);
			// //Log.e("instance:", "-> " + infix.substring(i, i + 1));
			// //Log.e("postfix:", postfix.toString() + "");
			// //Log.e("stack:", stack.toString() + "");
			if (s.equals(" "))
				continue;
			if (IsOperand(s + "")) {
				number = "";
				while (IsOperand(s)) {
					number += s;
					i++;
					if (i < len) {
						s = "" + infix.charAt(i);
					} else {
						break;
					}
				}
				postfix.push((number + " "));
			} else if (s.equals("(")) {
				stack.push((s));
				// continue;//hieu
			} else if (s.equals(")")) {
				String x = stack.pop();
				while (x.equals("(") != true)// khi nao con x != "("
				{
					postfix.push((x + " "));
					x = stack.pop();
				}
			} else// IsOperator(s)
			{
				while (stack.isEmpty() != true
						&& getPriority(s) <= getPriority(stack.peek())) {
					postfix.push(stack.pop() + " ");
				}
				stack.push((s + ""));
			}
		}

		while (stack.isEmpty() != true) {
			postfix.push(stack.pop() + " ");
		}
		return postfix;
	}

	// private function
	private static int getPriority(String op) {
		op = op.replace(" ", "");
		if (op.equals("*") || op.equals("/") || op.equals("%")
				|| op.equals("x"))
			return 2;
		if (op.equals("+") || op.equals("-"))
			return 1;
		return 0;
	}

	protected boolean checkExpression(String expression) {
		int len = expression.length();
		Stack<Integer> bracket = new Stack<Integer>();
		for (int i = 0; i < len; i++) {
			char var = expression.charAt(i);
			if (var >= '0' && var <= '9')
				continue;
			else {

				switch (var) {
				case '+':
				case '-':
				case 'x':
				case '/':
				case '*':
				case ' ':
					break;
				case '(':
					bracket.push(1);
					break;
				case ')':
					if (bracket.isEmpty())
						return false;
					bracket.pop();
					break;
				default:
					return false;
				}

			}
		}
		if (bracket.isEmpty() != true)
			return false;
		return true;
	}

	private boolean chiaHet(double parseDouble, double parseDouble2) {
		if (parseDouble % parseDouble2 < EPXILON)
			return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	private double findHalfSplit(Stack<String> postfix, double can_duoi,
			double can_tren, String p, int expectedResult) {

		Stack<String> buf = (Stack<String>) postfix.clone();
		int size = buf.size();
		for (int i = 0; i < size; i++) {
			buf.set(i, buf.get(i).replace(p, can_duoi + ""));
		}
		buf = formatPostfix(buf);
		// f(a)
		double a = calcPostfix(buf);
		buf = (Stack<String>) postfix.clone();
		double mid = (can_duoi + can_tren) / 2;
		for (int i = 0; i < size; i++) {
			buf.set(i, buf.get(i).replace(p, mid + ""));
		}
		buf = formatPostfix(buf);
		// f(m)
		Log.e("LOI GI DAY?", buf.toString());
		double m = calcPostfix(buf);
		/*
		 * buf = (Stack<String>) postfix.clone(); for (int i = 0; i < size; i++)
		 * { buf.set(i, buf.get(i).replace(p, can_tren + "")); } buf =
		 * formatPostfix(buf);
		 */
		// f(b)
		// float b = calcPostfix(buf);
		if (Math.abs(can_duoi - can_tren) < EPXILON)
			return can_duoi;
		if (Math.abs(a) <= EPXILON) {// a==exp
			// //Log.e("Nghiem",can_duoi+"");
			return can_duoi;
		}
		// Log.e("CAN", "|" + can_duoi + "|" + mid + "|" + can_tren);
		if (a * m < 0 && Math.abs(a) > EPXILON && Math.abs(m) > EPXILON) {
			// Log.e("CJ", "Can duoi");
			return findHalfSplit(postfix, can_duoi, mid, p, expectedResult);
		} else {
			return findHalfSplit(postfix, mid, can_tren, p, expectedResult);
		}
	}

	private String formatInfixExpression(String expressionInput) {
		expressionInput = expressionInput.replace(" ", "");// remove blank space
		expressionInput = expressionInput.replace("*", "x");
		expressionInput = expressionInput.replace("(-", "((0-1)*");
		expressionInput = expressionInput.replace("*-", "*(0-1)");
		expressionInput = expressionInput.replace("/-", "/(0-1)");
		expressionInput = expressionInput.replace("+-", "-");
		expressionInput = expressionInput.replace("--", "+");
		expressionInput = expressionInput.replace("+", " + ");
		expressionInput = expressionInput.replace("-", " - ");
		expressionInput = expressionInput.replace("x", " x ");
		expressionInput = expressionInput.replace("/", " / ");
		expressionInput = expressionInput.replace("(", " ( ");
		expressionInput = expressionInput.replace(")", " ) ");
		expressionInput = expressionInput.replace("  ", " ");
		return expressionInput;
	}

	private Stack<String> formatPostfix(Stack<String> postFix) {
		int length = postFix.size();
		String tmp = "", temp = "";
		for (int i = 0; i < length; i++) {
			tmp = "";
			temp = "";
			tmp = postFix.get(i);
			if (tmp.length() > 1 && tmp.charAt(0) == '-'
					&& tmp.charAt(1) >= '0' && tmp.charAt(1) <= '9') {
				for (int j = 1; j < tmp.length(); j++) {
					temp += tmp.charAt(j);
				}
				postFix.set(i, "x");
				postFix.insertElementAt("-", i);
				postFix.insertElementAt("1", i);
				postFix.insertElementAt("0", i);
				postFix.insertElementAt(temp, i);
			}
		}
		return postFix;
	}

	private boolean isNotSet(String x1) {
		x1 = x1.replace(" ", "");
		char var = x1.charAt(0);
		if (var >= 'A' && var <= 'Z') {
			return false;
		}
		return true;
	}

	private boolean IsOperand(String buffer) {
		int len = buffer.length();
		boolean is_operand = true;
		for (int i = 0; i < len; i++) {
			if (buffer.charAt(i) >= 'A' && buffer.charAt(i) <= 'Z')
				continue;
			if (buffer.charAt(i) == '.')
				continue;
			/*
			 * if (i + 1 < len && buffer.charAt(i) == 'E' && buffer.charAt(i +
			 * 1) == '-')
			 */
			if (buffer.contains("E-"))
				continue;
			if (buffer.charAt(i) < '0' || buffer.charAt(i) > '9')
				is_operand = false;
		}
		return is_operand;
	}

	@SuppressWarnings("unchecked")
	// Phuong phap NewTon
	private double searchResult_Newton(Stack<String> postfix, double can_duoi,
			double can_tren, String p, int expectedResult, boolean isOdd) {
		//Log.e("NEWTON", postfix.toString()+"|"+antiLag);
		antiLag++;
		if (isOdd) {
			return searchResult_halfSplit(postfix, -100, 100, p, expectedResult);
		}
		double fx0;
		Stack<String> stackFx = (Stack<String>) postfix.clone();
		int size = stackFx.size();
		for (int i = 0; i < size; i++) {
			stackFx.set(i, stackFx.get(i).replace(p, can_tren + ""));
		}
		stackFx = formatPostfix(stackFx);
		fx0 = calcPostfix(stackFx);
		//Log.e("DAO HAM d(x)", ">> "+dx + "");
		//Log.e("f(x0)", ">> "+fx0 + "");
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return can_tren - fx0 / dx;
	}

	@SuppressWarnings("unchecked")
	private double searchResult_halfSplit(Stack<String> postfix,
			double can_duoi, double can_tren, String p, int expectedResult) {
		//Log.e("PostFIx", postfix.toString());
		antiLag++;
		int limit = (int) (-can_duoi + can_tren);
		limit++;
		limit /= 0.25;
		double fragment = can_duoi;
		double Fxi[] = new double[limit];
		double Xi[] = new double[limit];
		int len = 0;
		Stack<String> bfStack = (Stack<String>) postfix.clone();
		for (int i = 0; i < limit; i++) {
			bfStack = (Stack<String>) postfix.clone();
			// //Log.e("searchResult_halfSplit", bfStack.toString()+"|"+p);
			len = bfStack.size();
			for (int j = 0; j < len; j++) {
				bfStack.set(j, bfStack.get(j).replace(p, fragment + ""));
			}
			bfStack = formatPostfix(bfStack);
			// //Log.e("BEFORE: searchResult_halfSplit",
			// bfStack.toString()+"|"+p);
			Fxi[i] = calcPostfix(bfStack);
			Xi[i] = fragment;
			// Log.e("FXi | X[" + i + "]", Fxi[i] + "|" + fragment);
			fragment += 0.25;
			if (fragment == 0) {
				fragment += 0.1;// khac khong
			}
		}
		double xi, xj;
		boolean count = true;
		for (int i = 0; i < limit; i++) {
			if (i < limit - 1) {
				xi = Fxi[i];
				xj = Fxi[i + 1];
				// Log.e("ij", xi + "|" + xj);
				if (xi * xj <= 0) {
					// Log.e("DX", dx + "|" + Xi[i] + "|" + Xi[i + 1]);
					// Log.e("Count", count + " @@!");
					if (dx < 0) {
						if (xj != 0) {
							// Log.e("pos can [ab]", postfix.toString() + "|"
							// + Xi[i] + "|" + Xi[i + 1] + "|" + p);

							return findHalfSplit(postfix, Xi[i], Xi[i + 1], p,
									expectedResult);
						}
					} else {
						if (count == false && xi != 0) {
							// Log.e("pos can [ab]", postfix.toString() + "|"
							// + Xi[i] + "|" + Xi[i + 1] + "|" + p);

							return findHalfSplit(postfix, Xi[i], Xi[i + 1], p,
									expectedResult);
						}
					}
					count = false;
				}
			}
		}
		return 0;
	}

	private LinkedList<String> selectionSort(LinkedList<String> varList) {
		String buf = "";
		int length = varList.size();
		for (int i = 0; i < length; i++) {
			for (int j = i; j < length; j++) {
				if (varList.get(i).charAt(0) > varList.get(j).charAt(0)) {
					buf = varList.get(i);
					varList.set(i, varList.get(j));
					varList.set(j, buf);
				}
			}
		}
		return varList;
	}

	// get random expression function:
	private String solveSimulator(Stack<String> postfix,
			LinkedList<String> varList, int min, int max, int expected) {
		String simuVar;
		postfix.push(expected + "");
		postfix.push("-");
		rd = new Randomize();
		// leave infinity loop var:
		// int cao = 0;
		while (true) {
			if(antiLag>LAGGING){
				antiLag=0;
				return "error";
			}
			@SuppressWarnings("unchecked")
			Stack<String> _postfix = (Stack<String>) postfix.clone();
			// Log.e("postfix", _postfix.toString());
			int sizePos = _postfix.size();
			int countVar = varList.size();
			simuVar = new String(expression);
			// Log.e("expression", expression);
			Stack<String> result = new Stack<String>();
			boolean Solved = true;
			String x1, x2;
			double rst;
			for (int i = 0; i < sizePos; i++) {
				if (!Solved)
					break;
				String p = _postfix.get(i);
				p = p.replace(" ", "");
				// //Log.e("PPP", p + "|");
				if (IsOperand(p)) {
					result.push(p);
				} else {
					switch (p.charAt(0)) {
					case '+':
						x2 = result.pop();
						x1 = result.pop();
						if (!isNotSet(x1)) {
							if (countVar > 1) {
								rd.setRangeValue(min, max);
								String newnum = rd.getRandomNumberNomal() + "";
								for (int j = 0; j < sizePos; j++) {
									_postfix.set(
											j,
											_postfix.get(j).replace(x1,
													"" + newnum));
								}
								_postfix = formatPostfix(_postfix);
								int INT_newnum = (int) Math.round(Double
										.parseDouble(newnum));
								simuVar = simuVar.replace(x1, INT_newnum + "");
								x1 = newnum;
								countVar--;
							} else {
								dx = 1;
								for (int j = 0; j < _postfix.size(); j++) {
									_postfix.set(j,
											_postfix.get(j).replace(" ", ""));
								}
								_postfix = formatPostfix(_postfix);
								double lastVar = searchResult_Newton(_postfix,
										CAN_DUOI, CAN_TREN, x1, expected, false);// recursive
								if (lastVar > 0) {// can
													// them
													// dk
									int INT_lastVar = (int) Math.round(lastVar);
									simuVar = simuVar.replace(x1, INT_lastVar
											+ "");
									;
									return simuVar;
								} else {
									Solved = false;
									break;
								}
							}
						}
						if (!isNotSet(x2)) {
							if (countVar > 1) {
								rd.setRangeValue(min, max);
								String newnum = rd.getRandomNumberNomal() + "";
								for (int j = 0; j < sizePos; j++) {
									_postfix.set(
											j,
											_postfix.get(j).replace(x2,
													"" + newnum));
								}
								_postfix = formatPostfix(_postfix);
								int INT_newnum = (int) Math.round(Double
										.parseDouble(newnum));
								simuVar = simuVar.replace(x2, INT_newnum + "");
								x2 = newnum;
								countVar--;
							} else {
								dx = 1;
								for (int j = 0; j < _postfix.size(); j++) {
									_postfix.set(j,
											_postfix.get(j).replace(" ", ""));
								}
								_postfix = formatPostfix(_postfix);
								double lastVar = searchResult_Newton(_postfix,
										CAN_DUOI, CAN_TREN, x2, expected, false);// recursive
								if (lastVar > 0) {// can
													// them
													// dk
									int INT_lastVar = (int) Math.round(lastVar);
									simuVar = simuVar.replace(x2, INT_lastVar
											+ "");
									// Log.e("simuVar", simuVar + "|" + x2);
									return simuVar;
								} else {
									Solved = false;
									break;
								}
							}
						}
						rst = Double.parseDouble(x1) + Double.parseDouble(x2);
						result.push(rst + "");
						break;
					case '*':
					case 'x':
						x2 = result.pop();
						x1 = result.pop();
						// Log.e("x1~x2", x1 + "~" + x2);
						if (!isNotSet(x1)) {
							if (countVar > 1) {
								rd.setRangeValue(min, max);
								String newnum = rd.getRandomNumber() + "";
								for (int j = 0; j < sizePos; j++) {
									_postfix.set(
											j,
											_postfix.get(j).replace(x1,
													"" + newnum));
								}
								_postfix = formatPostfix(_postfix);
								int INT_newnum = (int) Math.round(Double
										.parseDouble(newnum));
								// Log.e(simuVar, x1);
								simuVar = simuVar.replace(x1, INT_newnum + "");
								x1 = newnum;
								dx = Double.parseDouble(newnum);
								// Log.e("X1", x1 + "|");
								countVar--;
							} else {
								if (isNotSet(x2)) {
									dx = Double.parseDouble(x2);
								}
								for (int j = 0; j < _postfix.size(); j++) {
									_postfix.set(j,
											_postfix.get(j).replace(" ", ""));
								}
								double lastVar = searchResult_Newton(_postfix,
										CAN_DUOI, CAN_TREN, x1, expected, false);// recursive
								if (lastVar > 0) {// can
													// them
													// dk
									int INT_lastVar = (int) Math.round(lastVar);
									simuVar = simuVar.replace(x1, INT_lastVar
											+ "");
									;
									return simuVar;
								} else {
									Solved = false;
									break;
								}
							}
						}
						if (!isNotSet(x2)) {
							if (countVar > 1) {
								rd.setRangeValue(min, max);
								String newnum = rd.getRandomNumber() + "";
								for (int j = 0; j < sizePos; j++) {
									_postfix.set(
											j,
											_postfix.get(j).replace(x2,
													"" + newnum));
								}
								_postfix = formatPostfix(_postfix);
								int INT_newnum = (int) Math.round(Double
										.parseDouble(newnum));
								simuVar = simuVar.replace(x2, INT_newnum + "");
								x2 = newnum;
								dx = Double.parseDouble(newnum);
								countVar--;
							} else {
								if (isNotSet(x1)) {
									dx = Double.parseDouble(x1);
								}
								for (int j = 0; j < _postfix.size(); j++) {
									_postfix.set(j,
											_postfix.get(j).replace(" ", ""));
								}
								double lastVar = searchResult_Newton(
										formatPostfix(_postfix), CAN_DUOI,
										CAN_TREN, x2, expected, false);// recursive
								if (lastVar > 0) {// can
													// them
													// dk
									int INT_lastVar = (int) Math.round(lastVar);
									simuVar = simuVar.replace(x2, INT_lastVar
											+ "");

									// Log.e("simuVar", simuVar + "|" + x2);
									return simuVar;
								} else {
									Solved = false;
									break;
								}
							}
						}
						rst = Double.parseDouble(x1) * Double.parseDouble(x2);
						result.push(rst + "");
						break;
					case '-':
						x2 = result.pop();
						x1 = result.pop();
						if (!isNotSet(x1)) {
							if (countVar > 1) {
								if (isNotSet(x2))
									rd.setRangeValue(min,
											(int) Double.parseDouble(x2) - 1);
								else
									rd.setRangeValue(min, max);
								String newnum = rd.getRandomNumber() + "";
								for (int j = 0; j < sizePos; j++) {
									_postfix.set(
											j,
											_postfix.get(j).replace(x1,
													"" + newnum));
								}
								_postfix = formatPostfix(_postfix);
								int INT_newnum = (int) Math.round(Double
										.parseDouble(newnum));
								// Log.e(simuVar, x1);
								simuVar = simuVar.replace(x1, INT_newnum + "");
								x1 = newnum;
								// Log.e("X1", x1 + "|");
								countVar--;
							} else {
								dx = 1;
								for (int j = 0; j < _postfix.size(); j++) {
									_postfix.set(j,
											_postfix.get(j).replace(" ", ""));
								}
								double lastVar = searchResult_Newton(_postfix,
										CAN_DUOI, CAN_TREN, x1, expected, false);// recursive
								if (lastVar > 0) {// can
													// them
													// dk
									int INT_lastVar = (int) Math.round(lastVar);
									simuVar = simuVar.replace(x1, INT_lastVar
											+ "");
									;
									return simuVar;
								} else {
									Solved = false;
									break;
								}
							}
						}
						if (!isNotSet(x2)) {
							if (countVar > 1) {
								if (isNotSet(x1))
									rd.setRangeValue(min,
											(int) Double.parseDouble(x1) - 1);
								else
									rd.setRangeValue(min, max);
								String newnum = rd.getRandomNumber() + "";
								for (int j = 0; j < sizePos; j++) {
									_postfix.set(
											j,
											_postfix.get(j).replace(x2,
													"" + newnum));
								}
								_postfix = formatPostfix(_postfix);
								int INT_newnum = (int) Math.round(Double
										.parseDouble(newnum));
								simuVar = simuVar.replace(x2, INT_newnum + "");
								x2 = newnum;
								countVar--;
							} else {
								dx = -1;
								for (int j = 0; j < _postfix.size(); j++) {
									_postfix.set(j,
											_postfix.get(j).replace(" ", ""));
								}
								double lastVar = searchResult_Newton(
										formatPostfix(_postfix), CAN_DUOI,
										CAN_TREN, x2, expected, false);// recursive

								if (lastVar > 0) {// can
													// them
													// dk
									int INT_lastVar = (int) Math.round(lastVar);
									simuVar = simuVar.replace(x2, INT_lastVar
											+ "");

									// Log.e("simuVar", simuVar + "|" + x2);
									return simuVar;
								} else {
									Solved = false;
									break;
								}
							}
						}
						rst = Double.parseDouble(x1) - Double.parseDouble(x2);
						result.push(rst + "");
						break;
					case '/':
						x2 = result.pop();
						x1 = result.pop();
						// Log.e("x1~x2", x1 + "~" + x2);
						if (!isNotSet(x1)) {
							if (countVar > 1) {
								rd.setRangeValue(min, max);
								String newnum = rd.getRandomNumber() + "";
								for (int j = 0; j < sizePos; j++) {
									_postfix.set(
											j,
											_postfix.get(j).replace(x1,
													"" + newnum));
								}
								_postfix = formatPostfix(_postfix);
								int INT_newnum = (int) Math.round(Double
										.parseDouble(newnum));
								// Log.e(simuVar, x1);
								simuVar = simuVar.replace(x1, INT_newnum + "");
								x1 = newnum;
								// Log.e("X1", x1 + "|");
								countVar--;
							} else {
								for (int j = 0; j < _postfix.size(); j++) {
									_postfix.set(j,
											_postfix.get(j).replace(" ", ""));
								}
								dx = Double.parseDouble(x1);
								double lastVar = searchResult_Newton(_postfix,
										CAN_DUOI, CAN_TREN, x1, expected, true);// recursive
								if (lastVar > 0) {// can
													// them
													// dk
									int INT_lastVar = (int) Math.round(lastVar);
									simuVar = simuVar.replace(x1, INT_lastVar
											+ "");
									;
									return simuVar;
								} else {
									Solved = false;
									break;
								}
							}
						}
						if (!isNotSet(x2)) {
							if (countVar > 1) {
								rd.setRangeValue(min + 1, max);
								String newnum = rd.getRandomNumber() + "";
								if (isNotSet(x1)) {
									rd.setRangeValue(min + 1,
											(int) Double.parseDouble(x1) - 1);
									newnum = rd.getRandomNumber() + "";
									while (true) {
										if (chiaHet(Double.parseDouble(newnum),
												Double.parseDouble(x1))) {
											break;
										}
										newnum = rd.getRandomNumber() + "";
									}
								}
								for (int j = 0; j < sizePos; j++) {
									_postfix.set(
											j,
											_postfix.get(j).replace(x2,
													"" + newnum));
								}
								_postfix = formatPostfix(_postfix);
								int INT_newnum = (int) Math.round(Double
										.parseDouble(newnum));
								simuVar = simuVar.replace(x2, INT_newnum + "");
								x2 = newnum;
								countVar--;
							} else {
								if (isNotSet(x1)) {
									dx = Double.parseDouble(x1);
								}
								for (int j = 0; j < _postfix.size(); j++) {
									_postfix.set(j,
											_postfix.get(j).replace(" ", ""));
								}
								double lastVar = searchResult_Newton(
										formatPostfix(_postfix), CAN_DUOI,
										CAN_TREN, x2, expected, true);// recursive
								if (lastVar > 0) {// can
													// them
													// dk
									int INT_lastVar = (int) Math.round(lastVar);
									simuVar = simuVar.replace(x2, INT_lastVar
											+ "");

									// Log.e("simuVar", simuVar + "|" + x2);
									return simuVar;
								} else {
									Solved = false;
									break;
								}
							}
						}
						rst = Double.parseDouble(x1) / Double.parseDouble(x2);
						result.push(rst + "");
						break;
					}
				}
			}
			if (Solved == true)
				return simuVar;
			else
				break;
		}
		return "error";
	}
	
	private int daoHam(Stack<String> postf, String var){
		var = var.replace(" ", "");
		char varChar = var.charAt(0);
		int size = postf.size();
		int countDaoHam = 0;
		String buf = "";
		for (int i = 0; i < size ; i++) {
			buf = postf.get(i);
			if(buf.contains(""+varChar)){
				countDaoHam++;
			}
		}
		return countDaoHam;
	}
}
