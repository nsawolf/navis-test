using Blackjack;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Xunit;

namespace BlackjackTest
{
    class X { }

    public class InstanceFactoryTests
    {
        [Fact]
        public void can_be_used_with_singleton_builder()
        {
            X x = new BlackjackTest.X();
            InstanceFactory<X> f = new InstanceFactory<X>(SingletonBuilder<X>.builder(x));

            Assert.Equal(x, f.make());
            Assert.Equal(f.make(), f.make());
        }


        [Fact]
        public void normally_returns_new_value_from_lambda_for_each_build()
        {
            InstanceFactory<X> factory = new InstanceFactory<X>(() => new X());

            Assert.NotEqual(factory.make(), factory.make());
        }

        [Fact]
        public void requires_a_builder_function_which_produces_the_value()
        {
            InstanceFactory<Int32> factory = new InstanceFactory<Int32>(() => 42);

            int actual = factory.make();

            Assert.Equal(42, actual);
        }

        [Fact]
        public void allows_user_to_override_lambda_in_a_try_with_resources()
        {
            InstanceFactory<Int32> factory = new InstanceFactory<Int32>(() => 42);
            using (InstanceFactory<Int32> r = factory.overide(() => 12))
            {
                Assert.Equal(12, factory.make());
            }
        }

        [Fact]
        public void resumes_original_value_after_override_in_try_with_resources()
        {
            InstanceFactory<Int32> factory = new InstanceFactory<Int32>(() => 42);
            using (InstanceFactory<Int32> r = factory.overide(() => 12))
            {
            }
            Assert.Equal(42, factory.make());
        }
    }
}
