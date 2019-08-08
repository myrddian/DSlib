using System;
using System.Collections.Generic;
using MathNet.Numerics.Distributions;

namespace DSLib
{
    public class GuassianMAP
    {
        private NDistDescriptor defaultPrior;
        private int minJump = -10;
        private int maxJump = 10;
        private ContinuousUniform uniformSampler;

        public GuassianMAP()
        {
            defaultPrior = new NDistDescriptor();
            defaultPrior.Mean = 0;
            defaultPrior.Variance = 1;
        }


        private Double newVariance(List<Double> values, NDistDescriptor target)
        {
            Double newVariance = 1;



            return newVariance;
        }

        public NDistDescriptor findMAP(List<Double> values, int iterations)
        {
            NDistDescriptor returnValue = new NDistDescriptor();
            uniformSampler = new ContinuousUniform(minJump, maxJump);
            Double bestLogLikelihood = Double.NegativeInfinity;
            NDistDescriptor newDist = new NDistDescriptor();

            for (int counter = 0; counter < iterations; ++counter)
            {
                newDist.Mean = returnValue.Mean + uniformSampler.Sample();
            }

            return returnValue;

        }

    }
}
