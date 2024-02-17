package rocks.jerryPratt.javaGames.tdGammon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Random;

import org.junit.jupiter.api.Test;

public class TDGammonNeuralNetworkTest 
{

	@Test
	public void testWeightGradientComputation() 
	{
		Random random = new Random(1776L);
		
		double alpha = 0.1;
		double lambda = 0.0;
		double gamma = 1.0;
		
		int inputUnits = 6;
		int hiddenUnits = 4;
		
		TDGammonNeuralNetwork network = new TDGammonNeuralNetwork(alpha, lambda, gamma, inputUnits, hiddenUnits);
		
		double[] inputVector = new double[inputUnits];
		
		int numberOfTrainingSamples = 100;
		
		for (int i=0; i<numberOfTrainingSamples; i++)
		{
			generateRandomMatrix(random, 1.0, inputVector);
			
			double output = network.computeValue(inputVector);
			network.backpropogateDerivatives(inputVector);

			double[][] firstLayerWeightGradients = new double[inputUnits][hiddenUnits];
			double[] secondLayerWeightGradients = new double[hiddenUnits];

			network.getFirstLayerWeightGradients(firstLayerWeightGradients);
			network.getSecondLayerWeightGradients(secondLayerWeightGradients);
			
			double[][] firstLayerWeights = new double[inputUnits][hiddenUnits];
			double[] secondLayerWeights = new double[hiddenUnits];

			network.getFirstLayerWeights(firstLayerWeights);
			network.getSecondLayerWeights(secondLayerWeights);
			
			double[][] firstLayerWeightDelta = new double[inputUnits][hiddenUnits];
			double[] secondLayerWeightDelta = new double[hiddenUnits];
			
			generateRandomMatrix(random, 0.001, firstLayerWeightDelta);
			generateRandomMatrix(random, 0.001, secondLayerWeightDelta);
			
			addTo(firstLayerWeights, firstLayerWeightDelta);
			addTo(secondLayerWeights, secondLayerWeightDelta);
			
			network.setFirstLayerWeights(firstLayerWeights);
			network.setSecondLayerWeights(secondLayerWeights);
			
			double newOutput = network.computeValue(inputVector);
			
			double delta = newOutput - output;
			
			double expectedDelta = multiply(firstLayerWeightGradients, firstLayerWeightDelta) + multiply(secondLayerWeightGradients, secondLayerWeightDelta);
			
			assertEquals(delta, expectedDelta, 1e-3);			
		}
	}
	
	private void generateRandomMatrix(Random random, double maxValue, double[][] matrixToPack) 
	{
		for (int i=0; i<matrixToPack.length; i++)
		{
			for (int j=0; j<matrixToPack[0].length; j++)
			{
				matrixToPack[i][j] = -maxValue + 2.0 * maxValue * Math.random();
			}
		}		
	}
	
	private void generateRandomMatrix(Random random, double maxDelta, double[] deltaMatrix) 
	{
		for (int i=0; i<deltaMatrix.length; i++)
		{
			deltaMatrix[i] = -maxDelta + 2.0 * maxDelta * Math.random();
		}		
	}

	private double multiply(double[][] aMatrix, double[][] bMatrix) 
	{
		double ret = 0.0;
		
		for (int i=0; i<aMatrix.length; i++)
		{
			for (int j=0; j<aMatrix[0].length; j++)
			{
				ret = ret + aMatrix[i][j] * bMatrix[i][j];
			}
		}
		
		return ret;
	}
	
	private double multiply(double[] aMatrix, double[] bMatrix) 
	{
		double ret = 0.0;
		
		for (int i=0; i<aMatrix.length; i++)
		{
			ret = ret + aMatrix[i] * bMatrix[i];
		}
		
		return ret;
	}

	private void addTo(double[] aMatrix, double[] bMatrix) 
	{
		for (int i=0; i<aMatrix.length; i++)
		{
			aMatrix[i]+=bMatrix[i];
		}		
	}

	private void addTo(double[][] aMatrix, double[][] bMatrix) 
	{
		for (int i=0; i<aMatrix.length; i++)
		{
			for (int j=0; j<aMatrix[0].length; j++)
			{
				aMatrix[i][j]+=bMatrix[i][j];
			}
		}
	}

	@Test
	public void testSingleInputSingleOutput() 
	{
//		for (double x = -1.0; x<1.0; x+=0.1)
//		{
//			System.out.println("f(" + x + ") = " + doubleLogistic(x));
//		}
		
		Random random = new Random(1776L);
		
		double alpha = 0.5;
		double lambda = 0.0;
		double gamma = 1.0;
		
		int inputUnits = 1;
		int hiddenUnits = 1;
		
		TDGammonNeuralNetwork network = new TDGammonNeuralNetwork(alpha, lambda, gamma, inputUnits, hiddenUnits);
		
		double[] inputVector = new double[1];
		
		int numberOfTrainingSamples = 10000;
		
		for (int i=0; i<numberOfTrainingSamples; i++)
		{
			double x = generateRandomDouble(random, -1.0, 1.0);
			double y = doubleLogistic(x);
			
			inputVector[0] = x;
			network.updateNeuralNetwork(inputVector, y);
			
//			network.printWeights();
		}
		
		
		for (double x = -1.0; x<1.0; x+=0.1)
		{
			double output = network.computeValue(new double[]{x});
			double expected = doubleLogistic(x);
			
			assertEquals(expected, output, 0.1);
//			System.out.println("g(" + x + ") = " + network.computeValue(new double[]{x}));
		}
	}
	
	@Test
	public void testQuadraticFunction() 
	{		
		Random random = new Random(1776L);

		double alpha = 0.1;
		double lambda = 0.0;
		double gamma = 1.0;

		int inputUnits = 2;
		int hiddenUnits = 15;

		TDGammonNeuralNetwork network = new TDGammonNeuralNetwork(alpha, lambda, gamma, inputUnits, hiddenUnits);

      network.setFirstLayerActivationFunction(ActivationFunction.RectifiedLinear);
      network.setSecondLayerActivationFunction(ActivationFunction.Linear);
		
		double[] inputVector = new double[inputUnits];

		int numberOfTrainingSamples = 100000;

		for (int i=0; i<numberOfTrainingSamples; i++)
		{
			double x = generateRandomDouble(random, -1.0, 1.0);
			double y = generateRandomDouble(random, -1.0, 1.0);

			inputVector[0] = x;
			inputVector[1] = y;

			network.updateNeuralNetwork(inputVector, quadraticFunctionToLearn(x, y));

			//			network.printWeights();
		}


		boolean expectedEqualsOutput = true;
		
		for (double x = -1.0; x<1.0; x+=0.1)
		{
			for (double y = -1.0; y<1.0; y+=0.1)
			{
//				System.out.println("f(" + x + ", " + y + ") = " + quadraticFunctionToLearn(x, y));

				double output = network.computeValue(new double[]{x, y});
				double expected = quadraticFunctionToLearn(x, y);

				if (Math.abs(output - expected) > 0.12) expectedEqualsOutput = false;
//				System.out.println("g(" + x + ", " + y + ") = " + output);
			}
		}
		
		if (!expectedEqualsOutput)
      {
         network.printWeights();
         fail("Didn't match expected!");
      }
	}
	
	
	@Test
   public void testBinaryFunction() 
   {     
      Random random = new Random(1776L);

      double alpha = 0.05;
      double lambda = 0.0;
      double gamma = 1.0;

      int inputUnits = 8;
      int hiddenUnits = 20;

      TDGammonNeuralNetwork network = new TDGammonNeuralNetwork(alpha, lambda, gamma, inputUnits, hiddenUnits);

      network.setFirstLayerActivationFunction(ActivationFunction.Sigmoid);
      network.setSecondLayerActivationFunction(ActivationFunction.Sigmoid);
      
      int numberOfTrainingSamples = 1000000;

      for (int i=0; i<numberOfTrainingSamples; i++)
      {
         double[] binaryDigits = generateRandomBinaryInput(random, inputUnits);

         network.updateNeuralNetwork(binaryDigits, binaryFunctionToLearn(binaryDigits));

         //       network.printWeights();
      }

      int numberOfTests = 100;
      boolean expectedEqualsOutput = true;

      for (int i=0; i<numberOfTests; i++)
      {
         double[] binaryDigits = generateRandomBinaryInput(random, inputUnits);


         double output = network.computeValue(binaryDigits);
         double expected = binaryFunctionToLearn(binaryDigits);

         if (Math.abs(output - expected) > 0.25) expectedEqualsOutput = false;
//         System.out.println("output = " + output + ", expected = " + expected);
      }

      if (!expectedEqualsOutput)
      {
         network.printWeights();
         fail("Didn't match expected!");
      }
   }
	
	
	private double binaryFunctionToLearn(double[] binaryDigits)
   {
      boolean b0 = convertToBoolean(binaryDigits[0]);
      boolean b1 = convertToBoolean(binaryDigits[1]);
      boolean b2 = convertToBoolean(binaryDigits[2]);
      boolean b3 = convertToBoolean(binaryDigits[3]);
      boolean b4 = convertToBoolean(binaryDigits[4]);
	   
      boolean result = b0 && (!b1) && (b2 ^ b3) || b4;
      
      return convertToDouble(result);
   }
	
	private boolean convertToBoolean(double digit)
	{
	   if (digit > 0.5) return true;
	   return false;
	}
	
	private double convertToDouble(boolean bool)
	{
	   if (bool) return 1.0;
	   return 0.0;
	}

   private double[] generateRandomBinaryInput(Random random, int numberOfDigits)
   {
      double[] randomBinaryDigits = new double[numberOfDigits];
      
      for (int i=0; i<numberOfDigits; i++)
      {
         boolean nextBoolean = random.nextBoolean();
         if (nextBoolean) randomBinaryDigits[i] = 1.0;
         else randomBinaryDigits[i] = 0.0;
      }
      
      return randomBinaryDigits;
   }

   @Test
	public void testSimpleLinearFunction() 
	{		
		Random random = new Random(1776L);

		double alpha = 0.01;
		double lambda = 0.0;
		double gamma = 0.0;

		int inputUnits = 2;
		int hiddenUnits = 4;

		TDGammonNeuralNetwork network = new TDGammonNeuralNetwork(alpha, lambda, gamma, inputUnits, hiddenUnits);
      network.setFirstLayerActivationFunction(ActivationFunction.Linear);
      network.setSecondLayerActivationFunction(ActivationFunction.Linear);
		
		double[] inputVector = new double[inputUnits];

		int numberOfTrainingSamples = 10000;

		for (int i=0; i<numberOfTrainingSamples; i++)
		{
			double x = generateRandomDouble(random, -1.0, 1.0);
			double y = generateRandomDouble(random, -1.0, 1.0);

			inputVector[0] = x;
			inputVector[1] = y;

			network.updateNeuralNetwork(inputVector, linearFunctionToLearn(x, y));

			//			network.printWeights();
		}


		for (double x = -1.0; x<1.0; x+=0.1)
		{
			for (double y = -1.0; y<1.0; y+=0.1)
			{
//				System.out.println("f(" + x + ", " + y + ") = " + linearFunctionToLearn(x, y));

				double output = network.computeValue(new double[]{x, y});
				double expected = linearFunctionToLearn(x, y);

				assertEquals(expected, output, 0.001);
//				System.out.println("g(" + x + ") = " + output);
			}
		}
	}

	private double doubleLogistic(double x) 
	{
		return TDGammonNeuralNetwork.logisticFunction(TDGammonNeuralNetwork.logisticFunction(x));
	}
	
	private double quadraticFunctionToLearn(double x, double y) 
	{
		return 0.15 * (x*x + x*y + 3.0 * y*y);
	}
	
	private double linearFunctionToLearn(double x, double y) 
	{
		return 0.3 * x - 0.1 * y + 0.2;
	}

	private double generateRandomDouble(Random random, double minValue, double maxValue) 
	{
		return minValue + random.nextDouble() * (maxValue - minValue);
	}

}
