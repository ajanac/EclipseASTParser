import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.math.*;

import javax.rmi.CORBA.Tie;
import javax.sound.midi.SysexMessage;

import java.util.Iterator;
 
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.internal.compiler.ast.Expression;
 
public class Example{
 
	//use of AST parser to parse the source string
	public static void parse(String str) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
        final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
 
		cu.accept(new ASTVisitor() {
            
			//HashSet for storing operands and operators
			Set operands = new HashSet();
			Set operators = new HashSet();
			
			//Count
			int operatorCount=0;
			int operandCount=0;
			 
			// Searching operators
			public void searchInOperatorArray(String opcode){
				//String array of 38 operators
				String[] operatorArray = new String[] {"=",">","<","!","~","?",":","->", "==", ">=","<=", "!=","&&","||","++","--","+","-","*","/","&","|","%","<<",">>",">>>","+=","-=","*=","/=","&=","|=","%=","<<=",">>=",">>>=","^","^="};
			    for (String i :operatorArray){
			    	if(i.equals(opcode)){
			    		operatorCount+=1;
			    		this.operators.add(opcode);
			    		}
			    }
			}
			
			//printing operator Hashset
			public void printOperatorSet(){
				System.out.println("Operators...");
			Iterator<String> it = operators.iterator();
		     while(it.hasNext())
		        System.out.print("\t"+it.next());
		     System.out.println("\n");
			}
			
			//printing operands hash set
			public void printOperandSet(){
				System.out.println("Operands");
			Iterator<String> it = operands.iterator();
		     while(it.hasNext())
		        System.out.print("\t"+it.next());
		     System.out.println("\n");
			}
			
			//Visit methods
			public boolean visit(VariableDeclarationFragment node) {
				return true; 	
			}
			public void endVisit(VariableDeclarationFragment node){
				SimpleName name = node.getName();
				this.operands.add(name.getIdentifier());
				operandCount+=1;
		     }
			
            public boolean visit(Assignment node){
			     return true;
				}
			public void endVisit(Assignment node){
				searchInOperatorArray(node.getOperator().toString());
				}
			
			public boolean visit(InfixExpression node){
		    	return true;
				}
			public void endVisit(InfixExpression node){
			    searchInOperatorArray(node.getOperator().toString());
				this.operands.add(node.getLeftOperand().toString());
				this.operands.add(node.getRightOperand().toString());
				operandCount+=2;
				}
			
			public boolean visit(ConditionalExpression node){	
				return true;
			}
			public void endVisit(ConditionalExpression node){
				searchInOperatorArray("?");
				searchInOperatorArray(":");
			}
			
			public boolean visit(PostfixExpression node){
				return true;
			}
			public void endVisit(PostfixExpression node){
				searchInOperatorArray(node.getOperator().toString());
				this.operands.add(node.getOperand().toString());
				operandCount+=1;
			}
			
			public boolean visit(PrefixExpression node){
				return true;
			}
			public void endVisit(PrefixExpression node){
				searchInOperatorArray(node.getOperator().toString());
				this.operands.add(node.getOperand().toString());
				operandCount+=1;
			}
			
			public boolean visit(TypeDeclaration node){
				return true;
			}
			public void endVisit(TypeDeclaration node){
				printOperatorSet();
				printOperandSet();
				computeHalstedComplexity();
			}
			
			//Halsted Complexity Computation 
			public void computeHalstedComplexity(){
				int n1=operators.size();
				int n2=operands.size();
				int N1=operatorCount;
				int N2=operandCount;
				
				if(n2!=0){
				//To avoid divide by error
			    int pgmVocabulary_n=n1+n2;
				int pgmLength_N=N1+N2;
				double calPgmLength=n1*(Math.log(n1)/Math.log(2))+n2*(Math.log(n2)/Math.log(2));
				double volumeV=pgmLength_N*(Math.log(pgmVocabulary_n)/Math.log(2));
				double difficulty_D=(n1/2)*(N2/n2);
				double effort_E=volumeV*difficulty_D;
				double time_T=effort_E/18;
				double bugs_B=volumeV/3000;
				
				
				//Printing metrics
				System.out.println("*******Halsted Complexity***********");
				System.out.println("Program Vocabulary= "+pgmVocabulary_n);
				System.out.println("Program Length= "+pgmLength_N);
				System.out.println("Calculated Program Length: "+calPgmLength);
				System.out.println("Volume= "+volumeV);
				System.out.println("Difficulty= "+difficulty_D);
				System.out.println("Effort= "+effort_E);
				System.out.println("Time= "+time_T);
				System.out.println("Bugs= "+bugs_B);
				}
			}
			
		});
 
	}
 
	//Read file content into a string
	public static String readFileToString(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(6000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
        reader.close();
        return  fileData.toString();	
	}
 
	//Loop  directory to get file list
	public static void ParseFilesInDir() throws IOException{
		File dirs = new File(".");
	    // File to be parsed
		String dirPath1="C:/CodeRepository/JavaSampleSimpleExample/markdownj-master/markdownj-master/core/src/main/java/org/markdownj";
		File root = new File(dirPath1);
		File[] files = root.listFiles ( );
		String filePath = null;
 
		 for (File f : files ) {
			 filePath = f.getAbsolutePath();
			 if(f.isFile()){
				 parse(readFileToString(filePath));
			 }
		 }
	}
 
	public static void main(String[] args) throws IOException {
		ParseFilesInDir();
	}
}