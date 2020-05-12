# TreeCalculator
Calculator that can solve complex algebraic equations implemented using a tree.

This program implements a new version of arithmetic calculators that uses expression trees and performs the following:

1. Read valid arithmetic expressions (parentheses are well-matched), where each expression is in a single
line. Each arithmetic expression with operands (variables and/or constants) and operators.

2. Then convert it to an arithmetic expression in a binary tree structure, where each, internal node
represents an operator, and its subtrees represent operands. All operators are binary, so every operator
has both a left and a right operand. Leaf nodes represent either integers or variables.

3. Uses nodes in an expression tree. Nodes could be, a leaf node just contains a value (integer or variable);
or an internal node has an operator and two Nodes representing its operands.

4. If the expression contains operand variables, it should prompt the user with those variables to set their
values in order to evaluate the expressions.
